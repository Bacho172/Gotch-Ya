package com.example.adars.gotchya.Core.Threading.GhostThreads;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.camera2.CameraAccessException;
import android.net.Uri;
import android.os.Looper;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.example.adars.gotchya.Core.API.ImgurAPI;
import com.example.adars.gotchya.Core.API.UploadToImgurTask;
import com.example.adars.gotchya.Core.Threading.ThreadHelper;
import com.example.adars.gotchya.DataModel.DomainModel.ApplicationReport;
import com.example.adars.gotchya.DataModel.DomainModel.Device;
import com.example.adars.gotchya.DataModel.Repository.ApplicationReportRepository;
import com.example.adars.gotchya.R;
import com.example.adars.gotchya.Sensors.DeviceInfo;
import com.example.adars.gotchya.Sensors.GuardCamera;
import com.example.adars.gotchya.Sensors.LocationCaller;
import com.example.adars.gotchya.Sensors.SensorsDataCreator;
import com.example.adars.gotchya.Sensors.Sensors_data;
import com.example.adars.gotchya.Sensors.StandardAccelerometer;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

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

        this.activity.runOnUiThread(() -> {
            try {
                camera = new GuardCamera(this.activity, Looper.getMainLooper(), GuardCamera.SELFIE_CAMERA);
                cameraBack = new GuardCamera(this.activity, Looper.getMainLooper(), GuardCamera.BACK_CAMERA);
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        });
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

        final String[] frontCameraPhotoURI = {null};
        final String[] backCameraPhotoURI = {null};
        final byte[][] frontCameraPhotoBytes = new byte[1][1];

        activity.runOnUiThread(() -> {
            camera.takePhoto();
            frontCameraPhotoURI[0] = camera.getPhotoPath();
            frontCameraPhotoBytes[0] = camera.getPhotoBytes();
            System.out.println("LOCAL URI PHOTO......... " + frontCameraPhotoURI[0]);
        });
        delay(1000);


        Bitmap bitmap = null;
        try {
            bitmap = getThumbnail(this.activity, Uri.fromFile(new File(frontCameraPhotoURI[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }
        delay(500);
        System.out.println("BITMAP..." + bitmap);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
        byte[] data = bos.toByteArray();
        System.out.println("LOCAL PHOTO BYTES......... " + data);
//        activity.runOnUiThread(() -> {
//            cameraBack.takePhoto();
//            backCameraPhotoURL[0] = cameraBack.getPhotoPath();
//            System.out.println("LOCAL URL PHOTO (BACK)......... " + backCameraPhotoURL[0]);
//        });

        String postURL = null;
        try {
            postURL = ApplicationReportRepository.getInstance().postImageToServer(data, frontCameraPhotoURI[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("..postURL after...." + postURL);
        report.setFrontCameraImage(postURL);
        report.setBackCameraImage(UploadToImgurTask.NO_PICTURE_URL);
        report.setDevice(device);

        ApplicationReportRepository.getInstance().insert(report); // wysyłanie danych na serwer
        System.out.println("Zakończono wysyłanie danych !");
    }

    public static Bitmap getThumbnail(Activity activity, Uri uri) throws FileNotFoundException, IOException{
        InputStream input = activity.getApplicationContext().getContentResolver().openInputStream(uri);

        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();

        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1)) {
            return null;
        }

        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth)
                ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;

        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = originalSize;
        bitmapOptions.inDither = true; //optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//
        input = activity.getApplicationContext().getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }

    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 8192);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
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
