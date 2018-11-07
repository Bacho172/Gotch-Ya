package com.example.adars.gotchya;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.Core.Fonts;
import com.example.adars.gotchya.DataModel.DomainModel.User;

public class MainActivity extends AppCompatActivity {

    private Button buttonGetStarted;
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

        buttonGetStarted = findViewById(R.id.buttonGetStarted);
        buttonGetStarted.setOnClickListener((l) -> buttonGetStartedClick());

    }

    private void buttonGetStartedClick() {
        Toast.makeText(this, "Test",Toast.LENGTH_LONG).show();
    }

}
