package br.dev.app.wifi.domain.wifi;

/**
 * Created by jerab on 19/03/18.
 */

public class Wifi {

    private String SSID;
    private String preSharedKey;
    private String BSSID;

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getPreSharedKey() {
        return preSharedKey;
    }

    public void setPreSharedKey(String preSharedKey) {
        this.preSharedKey = preSharedKey;
    }
}
