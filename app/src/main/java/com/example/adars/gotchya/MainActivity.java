package com.example.adars.gotchya;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.adars.gotchya.Core.Fonts;
import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.DataModel.DataModel.UserModel;
import com.example.adars.gotchya.DataModel.DomainModel.User;
import com.example.adars.gotchya.Sensors.TestAccelometerActivity;

public class MainActivity extends AppCompatActivity {
    private Button buttonDeveloperMode;
    private ImageButton imageButtonGetStarted;

    private TextView textViewDescription1;
    private TextView textViewDescription2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonDeveloperMode=findViewById(R.id.button_developer_mode);
        textViewDescription1 = findViewById(R.id.textViewDescription);
        textViewDescription2 = findViewById(R.id.textViewDescription2);
        Functions.setFont(this, textViewDescription1, Fonts.FONT_AVENIR_NextLTProRegular);
        Functions.setFont(this, textViewDescription2, Fonts.FONT_AVENIR_NextLTProRegular);
        imageButtonGetStarted = findViewById(R.id.imageButtonGetStarted);
        imageButtonGetStarted.setOnClickListener((l) -> imageButtonGetStartedClick());
        buttonDeveloperMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent(getApplicationContext(),TestAccelometerActivity.class);
                startActivity(intent);
            }
        });
    }
    private void setButtonDeveloperModeClick(){
        startActivity(new Intent(this,TestAccelometerActivity.class));
    }
    private void imageButtonGetStartedClick() {
        User rememberedUser = Functions.loadUserData(this);
        if (rememberedUser != null) UserModel.getInstance().logIn(rememberedUser);
        startActivity(new Intent(this, rememberedUser != null ? MainMenuActivity.class : LogInActivity.class));
    }
}
