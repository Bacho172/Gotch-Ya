package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;
import com.example.adars.gotchya.DataModel.DomainModel.Device;
import com.example.adars.gotchya.R;
import com.example.adars.gotchya.Sensors.DeviceInfo;
import com.example.adars.gotchya.Sensors.LocationCaller;
import com.example.adars.gotchya.Sensors.StandardAccelerometer;

import java.util.Date;

/**
 * Created by Adam Bachorz on 28.12.2018.
 */
public class GhostTracker extends ThreadHelper {

    private StandardAccelerometer accelerometer;
    private LocationCaller locationCaller;
    private View view;
    private long sendingInterval;
    private long listenerInterval;
    private boolean phoneStolen = false;
    private long attackTime = 0;

    public GhostTracker() {
        super();
        this.activity = getStickyActivity();
        accelerometer = new StandardAccelerometer(activity.getApplicationContext());
        locationCaller = new LocationCaller(this.activity);
        view = this.activity.findViewById(R.id.main_menu_layout);
    }

    public GhostTracker(Activity activity, long listenerInterval, long sendingInterval) {
        super(activity, listenerInterval, true);
        this.sendingInterval = sendingInterval;
        this.listenerInterval = listenerInterval;
        accelerometer = new StandardAccelerometer(activity.getApplicationContext());
        locationCaller = new LocationCaller(this.activity);
        view = this.activity.findViewById(R.id.main_menu_layout);

    }

    @Override
    protected void onRun() {
        if (accelerometer.phoneIsMoving() || phoneStolen) {
            attackTime += listenerInterval;
            if (attackTime >= sendingInterval) {
                hitAlert();
                sendData();
                attackTime = 0;
            }
        } else System.out.println("OK");
    }

    private void sendData() {
        System.out.println("Pobieranie informacji do raportu !");
        DeviceInfo deviceInfo = new DeviceInfo();
        Device device = new Device();
        device.setID(111);
        device.setMacAddress(deviceInfo.getMAcAddress());

        ApplicationReport report = new ApplicationReport();
        report.setCreatedAt(new Date());
        report.setUpdatedAt(new Date());
        report.setDeviceIP("192.168.1.1");
        report.setSpeed((0.1 + Math.random() * 10) + "");
        report.setNearestObject("Uniwersytet Kazimierza Wielkiego");

        report.setCoordinates(locationCaller.getCoordinates());
        Snackbar.make(view, report.getCoordinates(), Snackbar.LENGTH_LONG).show();

        //TODO: Zdjęcia z kamer do URL !!!
        report.setFrontCameraImage("");
        report.setBackCameraImage("");
        report.setDevice(device);

        //ApplicationReportRepository.getInstance().insert(report);
    }

    @Override
    protected String reviveSpell() {
        return "KeepTracking";
    }

    public StandardAccelerometer getAccelerometer() {
        return accelerometer;
    }

    public void hitAlert() {
        if (!phoneStolen) phoneStolen = true;
    }

    public void cancelAlert() {
        if (phoneStolen) phoneStolen = false;
    }

    public void start() { startThread();}

    public void stop() { cancelAlert(); stopThread();}
}
