package br.dev.app.wifi.acitivities.wifilist;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.dev.app.wifi.R;

public class WifiListActivity extends AppCompatActivity {

    private WifiListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_list);

        presenter = new WifiListPresenter();
        presenter.requireWifiPermission(this, 0);
        presenter.loadWifiList(this, this, 0);




    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


        switch (requestCode) {
            case 0: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    presenter.loadWifiList(this, this, 0);


                } else {


                }
                return;
            }


        }
    }

}
