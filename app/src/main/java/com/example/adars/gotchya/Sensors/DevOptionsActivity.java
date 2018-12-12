package com.example.adars.gotchya.Sensors;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_options);
        textViewLatitude = findViewById(R.id.textViewLatitude);
        textViewLongitude = findViewById(R.id.textViewLongitude);
        Buttonstart = findViewById(R.id.buttonStart);
        Buttonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning=!isRunning;
                if (isRunning) {
                        startService(new Intent(getApplicationContext(), GPSService.class));

                }
            else{
                    stopService(new Intent(getApplicationContext(),GPSService.class));

                }
            }
        });
    }
}
