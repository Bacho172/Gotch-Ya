package com.example.adars.gotchya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.DataModel.DataModel.UserModel;
import com.example.adars.gotchya.DataModel.DomainModel.User;

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
        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Nie podano wszystkich danych",  Toast.LENGTH_LONG).show();
            return;
        }
        User user = UserModel.getInstance().logIn(login, password);
        if (user == null) {
            Toast.makeText(this, "Logowanie zakończone niepowodzeniem. \nZły login lub hasło",  Toast.LENGTH_LONG).show();
        } else {
            if (checkBoxRemember.isChecked()) {
                Functions.saveUserData(this, UserModel.getInstance().getCurrentUser());
            }
            startActivity(new Intent(this, MainMenuActivity.class));
        }

    }
}
