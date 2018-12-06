package com.example.adars.gotchya.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelometer implements SensorEventListener {
    private static final int US_TO_MS = 1000;
    private float lastAccelX;
    private float lastAccelY;
    private float lastAccelZ;
    private boolean active;
    private int samplingPeriodUs;
    private Sensor accelometerHandle;
    static private SensorManager sensorManager;

    public Accelometer(Context context, int samplingPeriod_ms) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelometerHandle = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        samplingPeriodUs = samplingPeriod_ms;
        samplingPeriodUs *= US_TO_MS;
        active = true;
        lastAccelX = 0;
        lastAccelY = 0;
        lastAccelZ = 0;
        sensorManager.registerListener(this, accelometerHandle, samplingPeriodUs);
    }

    public static Boolean isAvaible(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void sensorStop() {
        sensorManager.unregisterListener(this);
        active = false;
    }

    public void sensorStart() {
        sensorManager.registerListener(this, accelometerHandle, samplingPeriodUs);
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public void setSamplingPeriod(int samplingPeriod_ms) {
        samplingPeriodUs = samplingPeriod_ms;
        samplingPeriodUs *= US_TO_MS;
    }

    public int  getAccelX() {
        return (int)Math.abs(lastAccelX);
    }

    public int  getAccelY() {
        return (int)Math.abs(lastAccelY);
    }

    public int  getAccelZ() {
        return (int)Math.abs(lastAccelZ);
    }
    @Override
    public void onSensorChanged(SensorEvent event) {

        lastAccelX = event.values[0];
        lastAccelY = event.values[1];
        lastAccelZ = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        ;
    }
}
