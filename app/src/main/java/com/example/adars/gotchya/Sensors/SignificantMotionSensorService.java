package com.example.adars.gotchya.Sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.IBinder;
import android.widget.Toast;




public class SignificantMotionSensorService extends android.app.Service {
    private SensorManager sensorManager;
    private Sensor sensor;
    private TriggerEventListener triggerEventListener;
    private Boolean isMoving = false;

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
        triggerEventListener = new TriggerEventListener() {
            @Override
            public void onTrigger(TriggerEvent event) {
                Toast.makeText(getApplicationContext(), "moving", Toast.LENGTH_SHORT);
            }
        };
        sensorManager.requestTriggerSensor(triggerEventListener, sensor);
    }

    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

