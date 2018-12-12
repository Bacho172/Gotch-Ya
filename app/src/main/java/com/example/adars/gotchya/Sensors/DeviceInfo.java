package com.example.adars.gotchya.Sensors;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;

import com.example.adars.gotchya.LogInActivity;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class DeviceInfo {
    String email;
    String macAddress;
    String model;
    String name;
    String system;

    private String collectEmail() {
        return "dummy_email";
    }

    private String collectMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

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
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    private String collectModel() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        return manufacturer + " " + model;
    }

    private String collectName() {
        return "Telefon "+Build.MANUFACTURER;
    }

    private String collectSystem() {
        return "Android "+Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
    }

    void DeviceInfo() {

    }

    void collectData() {
        email = collectEmail();
        macAddress = collectMacAddress();
        model = collectModel();
        name = collectName();
        system = collectSystem();
    }

    public String getEmail() {
        return email;
    }

    public String getMAcAddress() {
        return macAddress;
    }

    public String getModel() {
        return model;
    }

    public String getName() {
        return name;
    }

    public String getSystem() {
        return system;
    }
}

