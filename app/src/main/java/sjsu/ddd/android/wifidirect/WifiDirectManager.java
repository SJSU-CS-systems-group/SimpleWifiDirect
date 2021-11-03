package sjsu.ddd.android.wifidirect;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


/**
 * Main WifiDirect class contains the various
 */
public class WifiDirectManager {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private Context context;
    private Lifecycle lifeCycle;
    private WifiDirectLifeCycleObserver lifeCycleObserver;
    private final IntentFilter intentFilter = new IntentFilter();
    private WifiDirectBroadcastReceiver receiver;

    /**
     * Ctor
     * @param context AppcompatActivity Context returned with a
     *                AppCompatActivity.getApplication() call
     * @param lifeCycle AppActivity Lifecycle returned with a
     *                  AppCompatActivity.getLifeCycle() call
     */
    public WifiDirectManager(Context context, Lifecycle lifeCycle) {
        this.context = context;
        this.initOwner(this.context);
        this.registerIntents();
        this.lifeCycle = lifeCycle;
        this.receiver = new WifiDirectBroadcastReceiver(this);
        this.lifeCycleObserver = new WifiDirectLifeCycleObserver(this.context, this, this.receiver);

        this.lifeCycle.addObserver(lifeCycleObserver);
    }

    private void initOwner(Context context) {
        this.manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(context, Looper.getMainLooper(), null);
    }

    private void initClient(Context context) {
        this.manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(context, Looper.getMainLooper(), null);
    }

    private void registerIntents() {
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }


    /**
     * Discovers WifiDirect peers for this device.
     * @return
     */
    @SuppressLint("MissingPermission")
    public CompletableFuture<Boolean> discoverPeers() {
        CompletableFuture<Boolean> cFuture = new CompletableFuture<>();
        this.manager.discoverPeers(this.channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
             cFuture.complete(true);
            }

            @Override
            public void onFailure(int reasonCode) {
                cFuture.complete(false);
            }
        });
        return cFuture;
    }
//@SuppressLint("MissingPermission")
//    public void discoverPeers() {
//        this.manager.discoverPeers(this.channel, new WifiP2pManager.ActionListener() {
//
//            @Override
//            public void onSuccess() {
//                Log.d("sDebug", " WifiDirect Discovery Success");
//            }
//
//            @Override
//            public void onFailure(int reasonCode) {
//                Log.d("sDebug", "WifiDirect Discovery Failure");
//            }
//        });
//    }

    @SuppressLint("MissingPermission")
    public Future<Boolean>  connect(WifiP2pConfig config) {
        CompletableFuture<Boolean> cFuture = new CompletableFuture<>();
        this.manager.connect(channel, config, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                cFuture.complete(true);
            }

            @Override
            public void onFailure(int reasonCode) {
                cFuture.complete(false);
            }
        });
        return cFuture;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Future<Boolean>  disconnect() {
        CompletableFuture<Boolean> cFuture = new CompletableFuture<>();
        this.manager.removeGroup(this.channel, new WifiP2pManager.ActionListener() {


            @Override
            public void onSuccess() {
                cFuture.complete(true);
            }

            @Override
            public void onFailure(int reasonCode) {
                cFuture.complete(false);
            }

        });
        return cFuture;
    }

    public void directConnection(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        config.wps.setup = WpsInfo.PBC;

        //((DeviceListFragment.DeviceActionListener) getActivity()).connect(config);
    }

    /**
     * "I want to see your manager"
     * @return this manager's manager
     */
    public WifiP2pManager getManager() {
        return this.manager;
    }

    /**
     * Get this manager's Channel
     * @return this WifiP2pManager.Channel
     */
    public WifiP2pManager.Channel getChannel() {
        return this.channel;
    }

    /**
     * Get this manager's IntentFilter with the intents registered
     * @return this IntentFilter
     */
    public IntentFilter getIntentFilter() {
        return this.intentFilter;
    }
}