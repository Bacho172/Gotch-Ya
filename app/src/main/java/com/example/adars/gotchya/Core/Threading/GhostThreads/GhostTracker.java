package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.net.Uri;
import android.os.Looper;
import android.view.View;

import com.example.adars.gotchya.Core.API.ImgurAPI;
import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DataModel.DeviceModel;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;
import com.example.adars.gotchya.DataModel.DomainModel.Device;
import com.example.adars.gotchya.DataModel.Repository.ApplicationReportRepository;
import com.example.adars.gotchya.R;
import com.example.adars.gotchya.Sensors.GuardCamera;
import com.example.adars.gotchya.Sensors.LocationCaller;
import com.example.adars.gotchya.Sensors.SensorsDataCreator;
import com.example.adars.gotchya.Sensors.Sensors_data;
import com.example.adars.gotchya.Sensors.StandardAccelerometer;

import java.io.File;
import java.io.IOException;

/**
 * Created by Adam Bachorz on 28.12.2018.
 */
public class GhostTracker extends ThreadHelper {

    private StandardAccelerometer accelerometer;
    private LocationCaller locationCaller;
    private View view;
    private GuardCamera camera;
    private GuardCamera cameraBack;
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
//        locationCaller = new LocationCaller(this.activity);
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
//        locationCaller = new LocationCaller(this.activity);
        view = this.activity.findViewById(R.id.main_menu_layout);
        if (!LOOPER_ATTACHED) {
            //Looper.prepare();
            LOOPER_ATTACHED = true;
        }

        this.activity.runOnUiThread(() -> {
            try {
                camera = new GuardCamera(this.activity, Looper.getMainLooper(), GuardCamera.SELFIE_CAMERA);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onRun() {
        if (accelerometer.phoneIsMoving() || phoneStolen) {
//            try {
                attackTime += listenerInterval;
                if (attackTime >= sendingInterval) {
                    hitAlert();
                    sendData();
                    attackTime = 0;
                }
//            } catch (Exception ex) {
//                System.err.println("!!!!!!!!!!!!!!  SENDING ERROR !!!!!!!!!!!!!!!!!!!\n"
//                        + ex.getMessage());
//            }
        } else System.out.println("OK");
    }

    private void sendData() {
        System.out.println("Pobieranie informacji do raportu !");
        //DeviceInfo deviceInfo = new DeviceInfo();
        Device device = new Device();
        device.setID(111);
        device.setMacAddress("30:A8:DB:8B:B5:D4");
        System.out.println("MAC....." + device.getMacAddress());

        ApplicationReport report = new ApplicationReport();
//        report.setCreatedAt(new Date());
//        report.setUpdatedAt(new Date());
        report.setDeviceIP(DeviceModel.getInstance().getIP());
        report.setSpeed((Math.random() > 0.5 ? 2 : 1) + "");

        String ukw = "Mikolaja Kopernika 1";
//          double latitude = 53.129041;
//          double longitude = 18.012499;

//          double shift = 0;
//          double predictedShift = Math.random() % 0.0001;
//          shift = predictedShift;
//          if (predictedShift <= 0.000999) shift = 0.011512;
//          latitude += shift;
//          String coordinates = String.format("%.8g", latitude) + "N18.012499E";
//          coordinates = coordinates.replace(',', '.');

        LocationCaller locationCaller = new LocationCaller(this.activity);
        report.setCoordinates(locationCaller.getCoordinates());
        String latitude = locationCaller.getLatitude() + "";
        String longitude = locationCaller.getLongitude() + "";
        Sensors_data sensorsData = SensorsDataCreator.createSensorData(activity.getBaseContext(), latitude, longitude);
//        Sensors_data sensorsData = SensorsDataCreator.createSensorData(activity.getBaseContext(), latitude + "", longitude + "");
        String streetName = sensorsData.getAddress().substring(0, sensorsData.getAddress().indexOf(","));
        report.setNearestObject(ukw);

        final String[] frontCameraPhotoURI = {null};

        activity.runOnUiThread(() -> {
            camera.takePhoto();
            frontCameraPhotoURI[0] = camera.getPhotoPath();
            System.out.println("LOCAL URI PHOTO......... " + frontCameraPhotoURI[0]);
        });
        delay(700);


        Bitmap bitmap = null;
        try {
            bitmap = Functions.bitmapFromURI(this.activity, Uri.fromFile(new File(frontCameraPhotoURI[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }
        delay(1000);

        System.out.println("BITMAP..." + bitmap);
        byte[] data = Functions.bitmapToByteArray(bitmap);
        System.out.println("LOCAL PHOTO BYTES......... " + data);

//        String postURL = null;
//        try {
//            postURL = ApplicationReportRepository.getInstance().postImageToServer(data, frontCameraPhotoURI[0]);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        report.setFrontCameraImagebyteArray(data);
        report.setDevice(device);

        try {
            ApplicationReportRepository.getInstance().insertV2(report, frontCameraPhotoURI[0]); // wysyłanie danych na serwer
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void stop() { cancelAlert(); stopThread();}

    public void pause() {pauseThread();}

    public void resume() {resumeThread();}
}
