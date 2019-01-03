package com.example.adars.gotchya.Sensors;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.adars.gotchya.R;

public class DevOptionsActivity extends AppCompatActivity {
    private Button Buttonstart;
    private GuardCamera guardCamera;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_options);
        Buttonstart = findViewById(R.id.buttonStart);
        try {
            guardCamera = new GuardCamera(getApplicationContext(), this);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        Buttonstart.setOnClickListener((l) -> start());
        //  Bitmap bitmap = guardCamera.getLastPhoto();

    }

    private void start() {
        guardCamera.takePhoto();
    }
}
