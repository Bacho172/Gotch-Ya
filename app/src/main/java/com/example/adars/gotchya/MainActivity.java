package com.example.adars.gotchya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.adars.gotchya.Core.Fonts;
import com.example.adars.gotchya.Core.Functions;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButtonGetStarted;

    private TextView textViewDescription1;
    private TextView textViewDescription2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDescription1 = findViewById(R.id.textViewDescription);
        textViewDescription2 = findViewById(R.id.textViewDescription2);
        Functions.setFont(this, textViewDescription1, Fonts.FONT_AVENIR_NextLTProRegular);
        Functions.setFont(this, textViewDescription2, Fonts.FONT_AVENIR_NextLTProRegular);

        imageButtonGetStarted = findViewById(R.id.imageButtonGetStarted);
        imageButtonGetStarted.setOnClickListener((l) -> imageButtonGetStartedClick());

    }

    private void imageButtonGetStartedClick() {
        startActivity(new Intent(this, LogInActivity.class));
    }

}
