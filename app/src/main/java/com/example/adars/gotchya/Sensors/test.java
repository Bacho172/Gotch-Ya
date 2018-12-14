package com.example.adars.gotchya.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.widget.Toast;

public class test extends TriggerEventListener {
    private SensorManager sensorManager;
    private Sensor sensor;
    private Boolean isMoving = false;
    private Context context;
       void test(Context context) {
          this.context=context;
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION);
            sensorManager.requestTriggerSensor(this, sensor);
        }

        @Override
        public void onTrigger(TriggerEvent event) {
           //  Toast.makeText(this.context, "moving", Toast.LENGTH_SHORT).show();
            sensorManager.requestTriggerSensor(this, sensor);
        }
    }

