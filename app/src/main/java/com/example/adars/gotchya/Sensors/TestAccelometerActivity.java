package com.example.adars.gotchya.Sensors;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.adars.gotchya.R;

public class TestAccelometerActivity extends AppCompatActivity {
    Button button_polacz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_accelometer);
        button_polacz= findViewById(R.id.button_polacz);
        button_polacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://portal-wertykalny.herokuapp.com/api/auth/google"));
                startActivity(browserIntent);
            }
        });
    }
}
