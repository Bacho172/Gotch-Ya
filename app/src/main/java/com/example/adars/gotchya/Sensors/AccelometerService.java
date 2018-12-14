package com.example.adars.gotchya.Sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AccelometerService extends Service implements SensorEventListener {
    private static final int US_TO_MS = 1000;
    private SensorManager sensorManager;
    private Sensor accelometer;
    private float lastAccelX = 0;
    private float lastAccelY = 0;
    private float lastAccelZ = 0;
    private float trigger = 0;
    private Boolean isMoving = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelometer, SensorManager.SENSOR_DELAY_NORMAL);
        return START_STICKY;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        lastAccelX = event.values[0];
        lastAccelY = event.values[1];
        lastAccelZ = event.values[2];
        String x = String.valueOf(lastAccelX);
        // Log.d("tag", x);
        Toast.makeText(getApplicationContext(), "x:" + String.valueOf(lastAccelX) + " y:" + String.valueOf(lastAccelY) + " z:" + String.valueOf(lastAccelZ), Toast.LENGTH_SHORT);
    }

  //  @Override
   /* public void onSensorChanged(SensorEvent event) {
        lastAccelX = event.values[0];
        lastAccelY = event.values[1];
        lastAccelZ = event.values[2];
        String x = String.valueOf(lastAccelX);
        // Log.d("tag", x);
        Toast.makeText(getApplicationContext(), "x:" + String.valueOf(lastAccelX) + " y:" + String.valueOf(lastAccelY) + " z:" + String.valueOf(lastAccelZ), Toast.LENGTH_SHORT);
        float diff = (float) Math.sqrt(Math.pow(lastAccelX, 2) + Math.pow(lastAccelY, 2) + Math.pow(lastAccelX, 2));
        if (diff > trigger) {
            isMoving = true;
        } else {
            isMoving = false;
        }
    }
*/
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
  /*  private class Accelometer implements SensorEventListener {
        public Accelometer(Context context) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            accelometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelometer, 1* US_TO_MS);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            lastAccelX = event.values[0];
            lastAccelY = event.values[1];
            lastAccelZ = event.values[2];
            String x = String.valueOf(lastAccelX);
            Log.d("tag", x);
               Toast.makeText(getApplicationContext(),"x:"+String.valueOf(lastAccelX)+" y:"+String.valueOf(lastAccelY)+" z:"+String.valueOf(lastAccelZ),Toast.LENGTH_LONG);
            float diff = (float) Math.sqrt(Math.pow(lastAccelX, 2) + Math.pow(lastAccelY, 2) + Math.pow(lastAccelX, 2));
            if (diff > trigger) {
                isMoving = true;
            } else {
                isMoving = false;
            }
            // Intent i = new Intent("accelometer_update");
            // i.putExtra("isMoving", String.valueOf(isMoving));
            // sendBroadcast(i);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
*/


