package com.phc.cim.Others;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by ali.abdul on 2/26/2018.
 */

public class Mac_Address extends AppCompatActivity{
    private static final String fileAddressMac = "/sys/class/net/wlan0/address"; //file path
    Context context;



    public String getMacAddress(Context context)//Call getMacAddress to get Mac Adress
    {
        this.context=context;
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
       // Toast.makeText(context, "Mac Address " + MacAdresse(wifiMan), Toast.LENGTH_SHORT).show();
        return MacAdresse(wifiMan);

    }

    public static String MacAdresse(WifiManager wifiMan) {

        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        if (wifiInf.getMacAddress().equals("02:00:00:00:00:00")) {
            String ret = null;
            try {
                ret = getAdressMacByInterface();
                if (ret != null) {
                    return ret;
                } else {
                    ret = getAddressMacByFile(wifiMan);
                    return ret;
                }
            } catch (IOException e) {
                Log.e("MobileAccess", "Erreur lecture propriete Adresse MAC");
            } catch (Exception e) {
                Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
            }
        } else {
            return wifiInf.getMacAddress();
        }
        return "02:00:00:00:00:00";
    }

    private static String getAdressMacByInterface()//This method will return mac adress just for 6.0 or may be for 6.0.1
    {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }

        } catch (Exception e) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
        }
        return null;
    }

    private static String getAddressMacByFile(WifiManager wifiMan) throws Exception {
        String ret;
        int wifiState = wifiMan.getWifiState();

        wifiMan.setWifiEnabled(true);
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        StringBuilder builder = new StringBuilder();
        int ch;
        while ((ch = fin.read()) != -1) {
            builder.append((char) ch);
        }

        ret = builder.toString();
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }
}
