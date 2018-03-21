package br.dev.app.wifi.acitivities.wifilist;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.dev.app.wifi.domain.wifi.Wifi;

/**
 * Created by jerab on 19/03/18.
 */

public class WifiListPresenter {

    private List<Wifi> wifiList;


    public void loadWifiList(Context context, Activity activity , int request_code){

        wifiList = new ArrayList<>();

        if(checkWifiPermission(context)== PackageManager.PERMISSION_GRANTED){

            final WifiManager mWifiManager = (WifiManager) activity.getApplicationContext().getSystemService(activity.WIFI_SERVICE);

            if(mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {

                // register WiFi scan results receiver
                IntentFilter filter = new IntentFilter();
                filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

                context.registerReceiver(new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        List<ScanResult> results = mWifiManager.getScanResults();
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
                            wifiList.add(w);
                        }
                    }
                }, filter);

                // start WiFi Scan
                mWifiManager.startScan();
            }


        }else{

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

}
