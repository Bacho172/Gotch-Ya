package com.example.adars.gotchya.Sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.adars.gotchya.R;

public class DevOptionsActivity extends AppCompatActivity {
    private Button Buttonstart;
    private TextView textViewLongitude;
    private TextView textViewLatitude;
    private Boolean isRunning = false;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_options);
        textViewLatitude = findViewById(R.id.textViewLatitude);
        textViewLongitude = findViewById(R.id.textViewLongitude);
        Buttonstart = findViewById(R.id.buttonStart);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (isRunning) {
                    Sensors_data dataFromBroadcast = SensorsReceiver.getSensors_data();
                    textViewLatitude.setText(dataFromBroadcast.latitude);
                    textViewLongitude.setText(dataFromBroadcast.longitde);
                }
            }
        };
        registerReceiver(receiver, new IntentFilter("new_data"));
        Buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = !isRunning;
                if (isRunning) {
                    startService(new Intent(getApplicationContext(), GPSService.class));
                    startService(new Intent(getApplicationContext(), AccelometerService.class));
                    Buttonstart.setText("Stop");
                } else {
                    stopService(new Intent(getApplicationContext(), GPSService.class));
                    stopService(new Intent(getApplicationContext(), Accelometer.class));
                    textViewLatitude.setText(" ");
                    textViewLongitude.setText(" ");
                    Buttonstart.setText("Start");

                }
            }
        });
    }
}
