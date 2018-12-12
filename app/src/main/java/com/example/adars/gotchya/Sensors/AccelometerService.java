package com.example.adars.gotchya.Sensors;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

public class AccelometerService extends Service {
    private static final int US_TO_MS = 1000;
    private SensorManager sensorManager;
    private Sensor accelometerHandle;
    private float lastAccelX = 0;
    private float lastAccelY = 0;
    private float lastAccelZ = 0;
    private float trigger = 0;
    private Boolean isMoving = false;

    private class Accelometer implements SensorEventListener {
        public Accelometer(Context context) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            accelometerHandle = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelometerHandle, 100 * US_TO_MS);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            lastAccelX = event.values[0];
            lastAccelY = event.values[1];
            lastAccelZ = event.values[2];
            float diff = (float) Math.sqrt(Math.pow(lastAccelX, 2) + Math.pow(lastAccelY, 2) + Math.pow(lastAccelX, 2));
            if (diff > trigger) {
                isMoving = true;
            } else {
                isMoving = false;
            }
            Intent i = new Intent("accelometer_update");
            i.putExtra("isMoving", String.valueOf(isMoving));
            sendBroadcast(i);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
