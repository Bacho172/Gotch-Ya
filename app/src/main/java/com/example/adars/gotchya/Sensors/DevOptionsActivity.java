package com.example.adars.gotchya.Sensors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.adars.gotchya.R;

public class DevOptionsActivity extends AppCompatActivity {
    private Button Buttonstart;
    private GuardCamera guardCameraBack;
    private GuardCamera guardCameraSelfie;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_options);
        Buttonstart = findViewById(R.id.buttonStart);
//        try {
//        //    guardCameraBack = new GuardCamera(this, GuardCamera.BACK_CAMERA);
//            guardCameraSelfie = new GuardCamera(this, GuardCamera.SELFIE_CAMERA);
//
//
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
        Buttonstart.setOnClickListener((l) -> start());
        //  Bitmap bitmap = guardCamera.getLastPhoto();

    }

    private void start() {
      // guardCameraBack.takePhoto();

       guardCameraSelfie.takePhoto();
       System.out.println("dev dev " + guardCameraSelfie.getPhotoPath());
    }
}
