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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import sjsu.ddd.android.wifidirect.WifiDirectManager;
import sjsu.ddd.android.wifidirect.WifiDirectBroadcastReceiver;

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

        // 3. Initialize the manager here
        wifiDirectManager = new WifiDirectManager(this.getApplication(), this.getLifecycle());

        // button for showing off discover peers
        final Button showToastBtn = findViewById(R.id.discoverPeers);
        showToastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CompletableFuture<Boolean> completedFuture = wifiDirectManager.discoverPeers();
                completedFuture.thenApply((b) -> {
                    Toast.makeText(MainActivity.this, "Did DiscoverPeers succeed?: " + b, Toast.LENGTH_SHORT).show();
                    return b;
                });
                String message = "Did i discover some peers?: ";
                Log.d(TAG, message);
            }
        });
    }
//    @Override
//    public void onResume() {
//        super.onResume();
//        receiver = new WifiDirectBroadcastReceiver(wifiDirectManager);
//        registerReceiver(receiver, wifiDirectManager.getIntentFilter());
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        unregisterReceiver(receiver);
//    }
}