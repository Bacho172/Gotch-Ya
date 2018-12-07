package com.example.adars.gotchya.Sensors;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adars.gotchya.R;

public class DevOptionsActivity extends AppCompatActivity {
    Button gps;
    Button accel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_options);
        accel=findViewById(R.id.buttonAccel);
        gps=findViewById(R.id.buttonGPS);
        accel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), TestAccelometerActivity.class);
                startActivity(intent);
            }
        });
        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),TestGPSActivity.class);
                startActivity(intent);
            }
        });
    }
}
