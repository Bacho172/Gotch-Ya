package com.example.adars.gotchya.Sensors;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class SignificantMotionSensorService extends android.app.Service {
    private SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isMoving = false;
    private SignificantMotionSensor significantMotionSensor;

    @Override
    public void onCreate() {
        super.onCreate();
         significantMotionSensor = new SignificantMotionSensor();
    }

    private class SignificantMotionSensor extends TriggerEventListener {
        void SignificantMotionSensor() {
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
            sensorManager.requestTriggerSensor(significantMotionSensor, sensor);
        }

        @Override
        public void onTrigger(TriggerEvent event) {
           // Log.e("tag","dziala");
            Toast.makeText(SignificantMotionSensorService.this, "moving", Toast.LENGTH_LONG).show();
            sensorManager.requestTriggerSensor(significantMotionSensor, sensor);
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

