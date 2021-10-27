package sjsu.ddd.android.wifidirect;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Koerfer on 10.03.2016.
 */
public class WifiDirectPeerAdapter extends BaseAdapter {

    private Context context;
    private List<WifiDirectPeerDevice> peerDevices;

    public WifiDirectPeerAdapter(Context context, List<WifiDirectPeerDevice> devices) {
        this.context = context;
        this.peerDevices = devices;
    }

    public void updatePeerDevices(List<WifiDirectPeerDevice> devices) {
        this.peerDevices = devices;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.peerDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return this.peerDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
