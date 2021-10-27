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
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class WifiDirectManager {

    public WifiP2pManager manager;
    public WifiP2pManager.Channel channel;
    private final IntentFilter intentFilter = new IntentFilter();

    public WifiDirectManager(Context context) {
        this.initOwner(context);
        this.registerIntents();
    }

    public void initOwner(Context context) {
        this.manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(context, Looper.getMainLooper(), null);
    }

    public void initClient(Context context) {
        this.manager = (WifiP2pManager) context.getSystemService(Context.WIFI_P2P_SERVICE);
        this.channel = this.manager.initialize(context, Looper.getMainLooper(), null);
    }

    public void registerIntents() {
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

//    /**
//     * Discovers WifiDirect peers for this device.
//     * @return
//     */
//    @SuppressLint("MissingPermission")
//    public Future<Boolean> discoverPeers() {
//        CompletableFuture<Boolean> cFuture = new CompletableFuture<>();
//        this.manager.discoverPeers(this.channel, new WifiP2pManager.ActionListener() {
//
//            @Override
//            public void onSuccess() {
//             cFuture.complete(true);
//            }
//
//            @Override
//            public void onFailure(int reasonCode) {
//                cFuture.complete(false);
//            }
//        });
//        return cFuture;
//    }
@SuppressLint("MissingPermission")
    public void discoverPeers() {
        this.manager.discoverPeers(this.channel, new WifiP2pManager.ActionListener() {

            @Override
            public void onSuccess() {
                Log.d("sDebug", " WifiDirect Discovery Success");
            }

            @Override
            public void onFailure(int reasonCode) {
                Log.d("sDebug", "WifiDirect Discovery Failure");
            }
        });
    }

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
     * @return
     */
    public WifiP2pManager.Channel getChannel() {
        return this.channel;
    }

    /**
     * Get this manager's IntentFilter with the intents registered
     * @return
     */
    public IntentFilter getIntentFilter() {
        return this.intentFilter;
    }
}