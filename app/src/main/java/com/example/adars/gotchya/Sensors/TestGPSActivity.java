package com.example.adars.gotchya.Sensors;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.adars.gotchya.R;

import org.w3c.dom.Text;

public class TestGPSActivity extends AppCompatActivity {
    Button refresh;
    TextView longitute;
    TextView latitute;
    GPS gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gps);
        refresh=findViewById(R.id.button_refresh);
        longitute=findViewById(R.id.textViewLongitute);
        latitute=findViewById(R.id.textViewLatitute);
        gps= new GPS(this);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                longitute.setText(String.valueOf(gps.getLongitute()));
                latitute.setText(String.valueOf(gps.getLongitute()));
            }
        });
    }
}
