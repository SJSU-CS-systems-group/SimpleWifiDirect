package sjsu.ddd.android.wifidirect;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager.ConnectionInfoListener;

public class DeviceConnectionInfoListener implements ConnectionInfoListener {

    WifiP2pInfo info;
    @Override
    public void onConnectionInfoAvailable(final WifiP2pInfo info) {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
        this.info = info;
        //this.getView().setVisibility(View.VISIBLE);

        // The owner IP is now known.
//        TextView view = (TextView) mContentView.findViewById(R.id.group_owner);
//        view.setText(getResources().getString(R.string.group_owner_text)
//                + ((info.isGroupOwner == true) ? getResources().getString(R.string.yes)
//                : getResources().getString(R.string.no)));

        // InetAddress from WifiP2pInfo struct.
//        view = (TextView) mContentView.findViewById(R.id.device_info);
//        view.setText("Group Owner IP - " + info.groupOwnerAddress.getHostAddress());

        // After the group negotiation, we assign the group owner as the file
        // server. The file server is single threaded, single connection server
        // socket.
        //
        // NOTE This is where we can register different file async tasks
        if (info.groupFormed && info.isGroupOwner) {
            // will uncomment
//            new FileServerAsyncTask(getActivity(), mContentView.findViewById(R.id.status_text))
//                    .execute();
        } else if (info.groupFormed) {
//            // The other device acts as the client. In this case, we enable the
//            // get file button.
//            mContentView.findViewById(R.id.btn_start_client).setVisibility(View.VISIBLE);
//            ((TextView) mContentView.findViewById(R.id.status_text)).setText(getResources()
//                    .getString(R.string.client_text));
        }

        // hide the connect button
        //mContentView.findViewById(R.id.btn_connect).setVisibility(View.GONE);
    }
}
