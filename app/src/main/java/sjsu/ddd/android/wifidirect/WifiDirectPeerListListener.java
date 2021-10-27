package sjsu.ddd.android.wifidirect;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WifiDirectPeerListListener implements WifiP2pManager.PeerListListener
{
    private List<WifiP2pDevice> devices = new ArrayList<>();

    @Override
    public void onPeersAvailable(WifiP2pDeviceList deviceList) {
        Collection<WifiP2pDevice> foundDevices = deviceList.getDeviceList();
        boolean removedDevices = this.devices.retainAll(foundDevices);
        boolean devicesAdded = this.devices.addAll(foundDevices);

        for(WifiP2pDevice d: this.devices) {
            Log.d("sDebug", d.toString());
        }
    }
}
