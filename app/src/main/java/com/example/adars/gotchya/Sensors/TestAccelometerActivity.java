package com.example.adars.gotchya.Sensors;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adars.gotchya.R;


public class TestAccelometerActivity extends AppCompatActivity {
    Button button_polacz;
    Button on;
    Button off;
    TextView x, y, z;
    Accelometer accel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_accelometer);
        button_polacz = findViewById(R.id.button_polacz);
        on = findViewById(R.id.button_accel_on);
        off = findViewById(R.id.button_accel_off);
        x = findViewById(R.id.textViewX);
        y = findViewById(R.id.textViewY);
        z = findViewById(R.id.textViewZ);
        accel = new Accelometer(this, 100);
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x.setText(String.valueOf(accel.getAccelX()));
                y.setText(String.valueOf(accel.getAccelY()));
                z.setText(String.valueOf(accel.getAccelX()));
            }
        });
        button_polacz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://portal-wertykalny.herokuapp.com/api/auth/google"));
                startActivity(browserIntent);
            }
        });
    }
}
