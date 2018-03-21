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

    public static List<Wifi> wifiList = null;



}
