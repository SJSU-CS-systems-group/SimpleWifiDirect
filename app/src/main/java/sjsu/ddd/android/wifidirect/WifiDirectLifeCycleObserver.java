package sjsu.ddd.android.wifidirect;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class WifiDirectLifeCycleObserver implements DefaultLifecycleObserver {

    private Context context;
    private WifiDirectManager manager;
    private WifiDirectBroadcastReceiver receiver;

    public WifiDirectLifeCycleObserver(Context context, WifiDirectManager manager, WifiDirectBroadcastReceiver receiver) {
        this.context = context;
        this.manager = manager;
        this.receiver = receiver;
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        this.context.registerReceiver(receiver, this.manager.getIntentFilter());
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        this.context.unregisterReceiver(receiver);
    }

}
