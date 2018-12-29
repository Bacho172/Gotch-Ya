package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;
import com.example.adars.gotchya.DataModel.DomainModel.Device;
import com.example.adars.gotchya.Sensors.DeviceInfo;
import com.example.adars.gotchya.Sensors.LocationCaller;
import com.example.adars.gotchya.Sensors.StandardAccelerometer;

import java.util.Date;

/**
 * Created by Adam Bachorz on 28.12.2018.
 */
public class GhostTracker extends ThreadHelper {

    private StandardAccelerometer acceleometer;
    private LocationCaller locationCaller;
    private long sendingInterval;
    private boolean phoneStolen = false;
    private View view;

    public GhostTracker() {
        super();
    }

    public GhostTracker(Context context, View view, long listenerInterval, long sendingInterval) {
        super(context, listenerInterval, true);
        this.sendingInterval = sendingInterval;
        this.view = view;
        acceleometer = new StandardAccelerometer(context);
        locationCaller = new LocationCaller(context);
    }

    @Override
    protected void onRun() {
        if (acceleometer.phoneIsMoving() || phoneStolen) {
            hitAlert();
            sendData();
            delay(sendingInterval);
        } else System.out.println("OK");
    }

    private void sendData() {
        System.out.println("Pobieranie informacji do raportu !");
        DeviceInfo deviceInfo = new DeviceInfo();
        Device device = new Device();
        device.setID(111);
        device.setMacAddress(deviceInfo.getMAcAddress());

        System.out.println("MAC: " + device.getMacAddress());
        Snackbar.make(view, "Test", Snackbar.LENGTH_SHORT).show();

        ApplicationReport report = new ApplicationReport();
        report.setCreatedAt(new Date());
        report.setUpdatedAt(new Date());
        report.setDeviceIP("192.168.1.1");
        report.setSpeed((0.1 + Math.random() * 10) + "");
        report.setNearestObject("Uniwersytet Kazimierza Wielkiego");

        report.setCoordinates(locationCaller.getCoordinates());
        System.out.println("Coords: " + report.getCoordinates());

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

    public StandardAccelerometer getAcceleometer() {
        return acceleometer;
    }

    public void hitAlert() {
        if (!phoneStolen) phoneStolen = true;
    }

    public void cancelAlert() {
        if (phoneStolen) phoneStolen = false;
    }

    public void start() {startThread();}

    public void stop() { cancelAlert(); stopThread();}
}
