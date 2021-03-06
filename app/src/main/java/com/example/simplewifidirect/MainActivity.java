package com.example.simplewifidirect;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import sjsu.ddd.android.wifidirect.WifiDirectBroadcastReceiver;
import sjsu.ddd.android.wifidirect.WifiDirectManager;

/**
 * 1. Entry point of Android app
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "wDebug";

    // 2. MainActivity requires only the WifiDirectManager
    private WifiDirectManager wifiDirectManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default onCreate Android code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 3.  We Initialize the manager here
        wifiDirectManager = new WifiDirectManager(this.getApplication(), this.getLifecycle());

        // 4. Buttons to test various WifiDirect functionality

        // DISCOVERPEERS button for discovering peers
        final Button dPeerButton = findViewById(R.id.discoverPeers);
        dPeerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompletableFuture<Boolean> completedFuture = wifiDirectManager.discoverPeers();
                completedFuture.thenApply((b) -> {
                    Toast.makeText(MainActivity.this, "Did DiscoverPeers succeed?: " + b, Toast.LENGTH_SHORT).show();
                    return b;
                });
                String message = "I tried to find some peers!: ";
                Log.d(TAG, message);
            }
        });

        // GETPEERS button for retrieving the list of peers
        // after clicking discover peers
        final Button gPeerButton = findViewById(R.id.getPeers);
        gPeerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<WifiP2pDevice> devices = wifiDirectManager.getPeerList();
                Log.d(TAG, "Logging Devices: \n");
                if(devices.isEmpty()) {
                    Log.d(TAG,"No devices found yet");
                }
                for(WifiP2pDevice d: devices) {
                    Log.d(TAG, d.toString());
                }
            }
        });

        // After clicking the DISCOVERPEERS button
        // this CONNECTTOHOST button will search through list of found devices
        // and connect to a device with the sjsu host name
        String SJSUHostDeviceName = "sjsu_host";
        final Button connectToHostPeerButton = findViewById(R.id.connectToHost);
        connectToHostPeerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<WifiP2pDevice> devices = wifiDirectManager.getPeerList();
                Log.d(TAG, "Logging Devices: \n");
                if(devices.isEmpty()) {
                    Log.d(TAG,"No devices found yet");
                }
                for(WifiP2pDevice d: devices) {
                    if(d.deviceName.contains(SJSUHostDeviceName))
                        wifiDirectManager.connect(wifiDirectManager.makeConfig(
                                d, false));
                    }
                }
        });

        // GETGROUPHOSTIP button
        // This button gets the IP of the host of the WifiDirect Group
        final Button getGroupHostIPButton = findViewById(R.id.getGroupHostIP);
        getGroupHostIPButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                String hostIP = wifiDirectManager.getGroupHostIP();
                Toast.makeText(MainActivity.this, "Group Host IP: " + hostIP, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "The group host IP is " + hostIP);
            }
        });

        // REMOVEGROUP BUTTON
        // Remove this device from the Wi-Fi Direct group.
        // Group is disbanded if this device is the owner
        // If the device is a client it will simply leave the group.
        final Button rmButton = findViewById(R.id.removeGroup);
        rmButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                CompletableFuture<Boolean> completedFuture = wifiDirectManager.removeGroup();
                completedFuture.thenApply((b) -> {
                    Toast.makeText(MainActivity.this, "Did removeGroup succeed?: " + b, Toast.LENGTH_SHORT).show();
                    return b;
                });
                String message = "I tried removing a group! ";
                Log.d(TAG, message);
            }
        });

        // CREATEGROUP Button
        // Legacy Wi-Fi Direct group creation
        // Group acts like a Wireless access point
        final Button cGroupButton = findViewById(R.id.createGroup);
        cGroupButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                String networkName = "DIRECT-XY-TestNetwork123";
                String password = "123456789";
                CompletableFuture<Boolean> completedFuture = wifiDirectManager.createGroup(
                        networkName, password);
                completedFuture.thenApply((b) -> {
                    Toast.makeText(MainActivity.this, "Did CreateGroup succeed?: " + b, Toast.LENGTH_SHORT).show();
                    return b;
                });
                String message = "I tried making a group! ";
                Log.d(TAG, message);
            }
        });

        // REQUESTGROUPINFO Button
        // Request the list of devices connected to
        // a Wi-Fi Direct Group.
        final Button cRButton = findViewById(R.id.requestGroupInfo);
        cRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompletableFuture<WifiP2pGroup> completedFuture = wifiDirectManager.requestGroupInfo();
                completedFuture.thenApply((b) -> {
                    Toast.makeText(MainActivity.this,
                            "I request groupIinfo: ", Toast.LENGTH_SHORT).show();
                    WifiP2pGroup group = b;
                    Collection<WifiP2pDevice> devices = group.getClientList();
                    Log.d(TAG, "Looping through group devices");
                    for(WifiP2pDevice d: devices) {
                        Log.d("wDebug", d.toString());
                    }
                    return b;
                });
                String message = "I tried requsting the group! ";
                Log.d(TAG, message);
            }
        });

    }
}
