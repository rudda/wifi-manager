package br.dev.app.wifi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import br.dev.app.wifi.acitivities.wifilist.WifiListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void listarWifi(View v){

        Intent it = new Intent(this, WifiListActivity.class);
        startActivity(it);

    }

}
