package com.example.adars.gotchya;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class LogInActivity extends AppCompatActivity {

    //TBE
    private EditText editTextLogin;
    private EditText editTextPassword;
    private CheckBox checkBoxRemember;
    private ImageButton imageButtonLogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);

        imageButtonLogIn = findViewById(R.id.imageButtonLogIn);
        imageButtonLogIn.setOnClickListener(l -> imageButtonLogInClick());
    }

    private void imageButtonLogInClick() {

    }
}
