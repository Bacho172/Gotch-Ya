package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.content.Context;
import android.widget.Toast;

import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;
import com.example.adars.gotchya.DataModel.DomainModel.Device;
import com.example.adars.gotchya.Sensors.DeviceInfo;
import com.example.adars.gotchya.Sensors.SensorsDataCreator;
import com.example.adars.gotchya.Sensors.Sensors_data;

import java.util.Date;

/**
 * Created by Adam Bachorz on 28.12.2018.
 */
public class GhostTracker extends ThreadHelper {

    public GhostTracker() {
        super();
    }

    public GhostTracker(Context context, long interval) {
        super(context, interval, true);
    }

    @Override
    protected void onRun() {
        System.out.println("Pobieranie informacji do raportu !");
        DeviceInfo deviceInfo = new DeviceInfo();
        Device device = new Device();
        device.setID(111);
        device.setMacAddress(deviceInfo.getMAcAddress());
        Toast.makeText(getContext(), device.getMacAddress(), Toast.LENGTH_LONG).show();

        Sensors_data sensorsData = SensorsDataCreator.createSensorData(this,"","");

        ApplicationReport report = new ApplicationReport();
        report.setCreatedAt(new Date());
        report.setUpdatedAt(new Date());
        report.setDeviceIP("192.168.1.1");
        report.setSpeed((0.1 + Math.random() * 10) + "");
        report.setNearestObject("Uniwersytet Kazimierza Wielkiego");
        report.setCoordinates(sensorsData.getLatitude() + sensorsData.getLongitde());

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

    public void start() {startThread();}

    public void stop() {stopThread();}
}
