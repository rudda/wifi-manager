package br.dev.app.wifi.acitivities.wifilist;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.dev.app.wifi.R;
import br.dev.app.wifi.adapter.WifiListAdapter;
import br.dev.app.wifi.domain.wifi.Wifi;

public class WifiListActivity extends AppCompatActivity implements WifiListAdapter.onWifiClick {

    private WifiListPresenter presenter;

    private RecyclerView recyclerView;
    private WifiListAdapter adapter;


    private List<Wifi> wifiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        wifiList = new ArrayList<>( );
        adapter = new WifiListAdapter( wifiList , WifiListActivity.this, this);


        //presenter = new WifiListPresenter();
        requireWifiPermission(this, 0);


        loadWifiList(WifiListActivity.this, WifiListActivity.this, 0);

        recyclerView.setAdapter( adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(WifiListActivity.this));
        adapter.notifyDataSetChanged();




    }





    public void loadWifiList(Context context, Activity activity , int request_code) {

        final WifiManager mWifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);


        wifiList = new ArrayList<>();
        adapter = new WifiListAdapter( wifiList , WifiListActivity.this, WifiListActivity.this);

        if (checkWifiPermission(context) == PackageManager.PERMISSION_GRANTED) {


            if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {

                // register WiFi scan results receiver
                IntentFilter filter = new IntentFilter();
                filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

                context.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        List<ScanResult> results = mWifiManager.getScanResults();

                        results = mWifiManager.getScanResults();
                        final int N = results.size();

                        String TAG = "MYWIFI";
                        Log.v(TAG, "Wi-Fi Scan Results ... Count:" + N);
                        for (int i = 0; i < N; ++i) {
                            Log.v(TAG, "  BSSID       =" + results.get(i).BSSID);
                            Log.v(TAG, "  SSID        =" + results.get(i).SSID);
                            Log.v(TAG, "  Capabilities=" + results.get(i).capabilities);
                            Log.v(TAG, "  Frequency   =" + results.get(i).frequency);
                            Log.v(TAG, "  Level       =" + results.get(i).level);
                            Log.v(TAG, "---------------");

                            Wifi w = new Wifi();
                            w.setSSID(results.get(i).SSID);
                            w.setBSSID(results.get(i).BSSID);
                            wifiList.add(w);
                            Log.i("WIFILIST", wifiList.size()+" "+wifiList.get(0).getSSID());
                            adapter = new WifiListAdapter(wifiList, WifiListActivity.this, WifiListActivity.this);
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }

                        Log.i("WIFILIST", wifiList.size()+" "+wifiList.get(1).getSSID());




                    }
                }, filter);

                // start WiFi Scan
                mWifiManager.startScan();
            }


        } else {

            requireWifiPermission(activity, request_code);

        }




    }


    public int checkWifiPermission(Context context){

        int permissionCheck = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CHANGE_WIFI_STATE);

        return  permissionCheck;

    }

    public void requireWifiPermission(Activity activity, int requestCode){

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CHANGE_WIFI_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CHANGE_WIFI_STATE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CHANGE_WIFI_STATE, Manifest.permission.ACCESS_COARSE_LOCATION },
                        requestCode);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

    }


    
    
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    loadWifiList(this, this, 0);


                } else {


                }
                return;
            }


        }
    }


    @Override
    public void cadastrarWifi(Wifi w) {

        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.BSSID = w.getBSSID();
        wifiConfiguration.SSID = "\"" + w.getSSID() + "\"";
        wifiConfiguration.preSharedKey = "\"apjerab!\"";

        //Conecto na nova rede criada.
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int netId = wifiManager.addNetwork(wifiConfiguration);
        wifiManager.saveConfiguration();
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);


        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);



        Log.i("WIFILIST", String.valueOf(wifiManager.getWifiState())+" state");


    }

    private boolean firstTime = true;

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(firstTime) {
                firstTime = false;
                return;
            }

            if(intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
                NetworkInfo networkInfo =
                        intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if(networkInfo.isConnected()) {
                    Toast.makeText(context, "conected as senhas batem :)", Toast.LENGTH_SHORT).show();
                }
                //Other actions implementation
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
         unregisterReceiver(receiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(this.receiver, new IntentFilter("android.net.wifi.STATE_CHANGE"));

    }
}
