package com.example.adars.gotchya;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.adars.gotchya.Core.Fonts;
import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.Sensors.DevOptionsActivity;
import com.example.adars.gotchya.Sensors.GuardCamera;

public class MainActivity extends AppCompatActivity {
    private Button buttonDeveloperMode;
    private ImageButton imageButtonGetStarted;
    private TextView textViewDescription1;
    private TextView textViewDescription2;
    private Button testCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        // buttonDeveloperMode=findViewById(R.id.button_developer_mode);
        textViewDescription1 = findViewById(R.id.textViewDescription);
        textViewDescription2 = findViewById(R.id.textViewDescription2);
        Functions.setFont(this, textViewDescription1, Fonts.FONT_AVENIR_NextLTProRegular);
        Functions.setFont(this, textViewDescription2, Fonts.FONT_AVENIR_NextLTProRegular);
        imageButtonGetStarted = findViewById(R.id.imageButtonGetStarted);
        imageButtonGetStarted.setOnClickListener((l) -> imageButtonGetStartedClick());
        startActivity(new Intent(this, DevOptionsActivity.class));

    }

    private void testCameraGetClick() {


    }


    private void imageButtonGetStartedClick() {
      /*
        User rememberedUser = Functions.loadUserData(this);
        if (rememberedUser != null) UserModel.getInstance().logIn(rememberedUser);
        startActivity(new Intent(this, rememberedUser != null ? MainMenuActivity.class : LogInActivity.class));
    */
        startActivity(new Intent(this, LogInActivity.class));

    }
}
