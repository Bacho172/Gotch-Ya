package com.example.adars.gotchya.Sensors;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
    BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };
    IntentFilter intentFilter=new IntentFilter("gps_update");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},1);
        startService(new Intent(this,GPSService.class));
        setContentView(R.layout.activity_test_gps);
        refresh=findViewById(R.id.button_refresh);
        longitute=findViewById(R.id.textViewLongitute);
        latitute=findViewById(R.id.textViewLatitute);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
