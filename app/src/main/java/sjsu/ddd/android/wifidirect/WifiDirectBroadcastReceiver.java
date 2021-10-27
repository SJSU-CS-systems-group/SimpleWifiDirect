package sjsu.ddd.android.wifidirect;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import com.example.simplewifidirect.MainActivity;

/**
 * A BroadcastReceiver that notifies of important wifi p2p events.
 * Acts as a event dispatcher to DeviceDetailFragment
 */
public class WifiDirectBroadcastReceiver extends BroadcastReceiver {

    private WifiDirectManager manager;
    //private MainActivity activity;
    private WifiDirectPeerListListener peerListListener;
    public static final String DEBUGTAG = "wDebug";

    /**
     * @param manager WifiDirectManager system service
     */
    public WifiDirectBroadcastReceiver(WifiDirectManager manager) {
        super();
        this.manager = manager;
        //this.activity = activity;
        this.peerListListener = new WifiDirectPeerListListener();
    }

    /*
     * (non-Javadoc)
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {

            // UI update to indicate wifi p2p status.
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi Direct mode is enabled
                //activity.setIsWifiP2pEnabled(true);
            } else {
//                activity.setIsWifiP2pEnabled(false);
//                activity.resetData();
            }
            Log.d(DEBUGTAG, "P2P state changed - " + state);
        }
        else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (manager != null) {
                  manager.getManager().requestPeers(manager.getChannel(), this.peerListListener);
            }
            Log.d(DEBUGTAG, "WifiDirectBroadcastReceiver INTENT Peers Changed");
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            if (manager == null) {
                return;
            }
            NetworkInfo networkInfo = (NetworkInfo) intent
                    .getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            if (networkInfo.isConnected()) {
                // we are connected with the other device, request connection
                // info to find group owner IP

//                DeviceDetailFragment fragment = (DeviceDetailFragment) activity
//                        .getFragmentManager().findFragmentById(R.id.frag_detail);
//                manager.requestConnectionInfo(channel, fragment);


            } else {
                // It's a disconnect
                //activity.resetData();
            }
        }
        else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}