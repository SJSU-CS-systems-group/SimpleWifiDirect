package sjsu.ddd.android.wifidirect;

import android.net.wifi.p2p.WifiP2pDevice;

public class WifiDirectPeerDevice {

    private WifiP2pDevice device;
    private boolean alreadyInConversation;

    public WifiDirectPeerDevice(WifiP2pDevice device, boolean alreadyInConversation) {
        this.device = device;
        this.alreadyInConversation = alreadyInConversation;
    }

    public WifiP2pDevice getDevice() {
        return this.device;
    }

    public boolean isAlreadyInConversation() {
        return this.alreadyInConversation;
    }
}