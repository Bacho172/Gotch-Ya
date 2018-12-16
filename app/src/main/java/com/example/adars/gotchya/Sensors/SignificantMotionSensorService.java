package com.example.adars.gotchya.Sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.IBinder;


public class SignificantMotionSensorService extends android.app.Service {
    public static final String SIGNIFICANT_MOTION_SENSOR_SERVICE_INTENT = "significant motion sensor service intent";
    public static final String SIGNIFICANT_MOTION_SENSOR_SERVICE_INTENT_EXTRA_IS_MOVING = "moving";
    private SensorManager sensorManager;
    private Sensor sensor;
    private SignificantMotionSensor significantMotionSensor;

    @Override
    public void onCreate() {
        super.onCreate();
        significantMotionSensor = new SignificantMotionSensor();
        sensorManager.requestTriggerSensor(significantMotionSensor, sensor);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private class SignificantMotionSensor extends TriggerEventListener {
        public SignificantMotionSensor() {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        }

        @Override
        public void onTrigger(TriggerEvent event) {
            Intent i = new Intent(SIGNIFICANT_MOTION_SENSOR_SERVICE_INTENT);
            i.putExtra(SIGNIFICANT_MOTION_SENSOR_SERVICE_INTENT_EXTRA_IS_MOVING, "true");
            sendBroadcast(i);
            sensorManager.requestTriggerSensor(significantMotionSensor, sensor);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

