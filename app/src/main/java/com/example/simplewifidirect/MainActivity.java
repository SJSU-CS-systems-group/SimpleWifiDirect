package com.example.simplewifidirect;

import androidx.appcompat.app.AppCompatActivity;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.content.Context;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import sjsu.ddd.android.wifidirect.WifiDirectManager;
import sjsu.ddd.android.wifidirect.WifiDirectBroadcastReceiver;

/**
 * 1. Entry point of android app
 */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "wDebug";

    // 2. Activity requires these two instance variables
    private WifiDirectManager wifiDirectManager;
    private WifiDirectBroadcastReceiver receiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default onCreate Android code
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 3. Initialize the manager here
        wifiDirectManager = new WifiDirectManager(this.getApplication());

        // button for testing
        final Button showToastBtn=findViewById(R.id.discoverPeers);
        showToastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"I am the discover peers fear me",Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Firing off the discover peers");

                // 4. Discover wifi direct Peers
                wifiDirectManager.discoverPeers();
                Log.d(TAG, "Finished discoverPeers onClick");
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        receiver = new WifiDirectBroadcastReceiver(wifiDirectManager);
        registerReceiver(receiver, wifiDirectManager.getIntentFilter());
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

// Future code please for later
//               Future<Boolean> completedFuture =  wifiDirectManager.discoverPeers();
//                try {
//                    Boolean result = completedFuture.get();
//                    String message = "Did i discover some peers?: " + result.booleanValue();
//                    Log.d(TAG, message);
//                } catch (ExecutionException e) {
//                    Log.d(TAG, "Execution exception for discover peers");
//                } catch (InterruptedException e) {
//                    Log.d(TAG, "Interrupt exception for discover peers");
//                }
}