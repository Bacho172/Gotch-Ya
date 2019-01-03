package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.app.Activity;
import android.hardware.camera2.CameraAccessException;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.adars.gotchya.Core.API.ImgurAPI;
import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;
import com.example.adars.gotchya.DataModel.DomainModel.Device;
import com.example.adars.gotchya.R;
import com.example.adars.gotchya.Sensors.DeviceInfo;
import com.example.adars.gotchya.Sensors.GuardCamera;
import com.example.adars.gotchya.Sensors.LocationCaller;
import com.example.adars.gotchya.Sensors.SensorsDataCreator;
import com.example.adars.gotchya.Sensors.Sensors_data;
import com.example.adars.gotchya.Sensors.StandardAccelerometer;

import java.util.Date;

/**
 * Created by Adam Bachorz on 28.12.2018.
 */
public class GhostTracker extends ThreadHelper {

    private StandardAccelerometer accelerometer;
    private LocationCaller locationCaller;
    private View view;
    private GuardCamera camera;
    private ImgurAPI imgurAPI;
    private long sendingInterval;
    private long listenerInterval;
    private boolean phoneStolen = false;
    private long attackTime;

    public static boolean LOOPER_ATTACHED = false;

    public static final String LISTENER_INTERVAL_LABEL = "listenerInterval";
    public static final String SENDING_INTERVAL_LABEL = "sendingInterval";

    public GhostTracker() {
        super();
        this.activity = getStickyActivity();
        this.sendingInterval = (long) getStickyValue(SENDING_INTERVAL_LABEL);
        this.listenerInterval = (long) getStickyValue(LISTENER_INTERVAL_LABEL);
        attackTime = sendingInterval;
        accelerometer = new StandardAccelerometer(activity.getApplicationContext());
        locationCaller = new LocationCaller(this.activity);
        view = this.activity.findViewById(R.id.main_menu_layout);
        //Looper.prepare();
    }

    public GhostTracker(Activity activity, long listenerInterval, long sendingInterval) {
        super(activity, listenerInterval, true);
        this.sendingInterval = sendingInterval;
        this.listenerInterval = listenerInterval;
        attackTime = sendingInterval;
        putNewStickyValue(LISTENER_INTERVAL_LABEL, listenerInterval);
        putNewStickyValue(SENDING_INTERVAL_LABEL, sendingInterval);
        accelerometer = new StandardAccelerometer(activity.getApplicationContext());
        locationCaller = new LocationCaller(this.activity);
        view = this.activity.findViewById(R.id.main_menu_layout);
        if (!LOOPER_ATTACHED) {
            //Looper.prepare();
            LOOPER_ATTACHED = true;
        }
    }

    public void loop() {
        while (running) {
            if (accelerometer.phoneIsMoving() || phoneStolen) {
                attackTime += listenerInterval;
                if (attackTime >= sendingInterval) {
                    hitAlert();
                    //sendData();
                    attackTime = 0;
                }
            } else System.out.println("OK");
        }
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
        report.setDeviceIP("192.168.1." + device.getID());
        report.setSpeed((0.1 + Math.random() * 10) + "");

        report.setCoordinates(locationCaller.getCoordinates());
        Snackbar.make(view, report.getCoordinates(), Snackbar.LENGTH_LONG).show();

        String latitude = locationCaller.getLatitude() + "";
        String longitude = locationCaller.getLongitude() + "";
        Sensors_data sensorsData = SensorsDataCreator.createSensorData(activity.getBaseContext(), latitude, longitude);
        String streetName = sensorsData.getAddress().substring(0, sensorsData.getAddress().indexOf(","));
        report.setNearestObject(streetName);

        String frontCameraPhotoURL = null;

        //runAsync(() -> {
            try {
                camera = new GuardCamera(this.activity, Looper.myLooper(), GuardCamera.BACK_CAMERA);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
            camera.takePhoto();
            String backCameraPhotoURL = null;

            //imgurAPI = new ImgurAPI(activity, camera.getPhotoPath());
            backCameraPhotoURL = camera.getPhotoPath();//imgurAPI.getPicturePath();
            System.out.println("URL PHOTO......... " + backCameraPhotoURL);
        //});

        report.setFrontCameraImage("");
        report.setBackCameraImage("");
        report.setDevice(device);

        //ApplicationReportRepository.getInstance().insert(report); // wysyłanie danych na serwer
        System.out.println("Zakończono wysyłanie danych !");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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

    public void stop() { cancelAlert(); Looper.myLooper().quit(); stopThread();}

    public void pause() {pauseThread();}

    public void resume() {resumeThread();}
}
