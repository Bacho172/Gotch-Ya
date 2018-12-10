package com.example.adars.gotchya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.adars.gotchya.Core.Functions;
import com.example.adars.gotchya.DataModel.DataModel.UserModel;
import com.example.adars.gotchya.DataModel.DomainModel.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

public class LogInActivity extends AppCompatActivity {

    //TBE
    private EditText editTextLogin;
    private EditText editTextPassword;
    private CheckBox checkBoxRemember;
    private ImageButton imageButtonLogIn;
    private SignInButton signInButtonGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);
        imageButtonLogIn = findViewById(R.id.imageButtonLogIn);
        imageButtonLogIn.setOnClickListener(l -> imageButtonLogInClick());
        signInButtonGoogle = findViewById(R.id.sign_in_button);
        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        signInButtonGoogle.setSize(SignInButton.SIZE_STANDARD);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account == null) {
           signInButtonGoogle.setVisibility(View.INVISIBLE);
        }
        else{
            signInButtonGoogle.setVisibility(View.VISIBLE);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);
    }

    private void imageButtonLogInClick() {
        String login = editTextLogin.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Nie podano wszystkich danych", Toast.LENGTH_LONG).show();
            return;
        }
        User user = UserModel.getInstance().logIn(login, password);
        if (user == null) {
            Toast.makeText(this, "Logowanie zakończone niepowodzeniem. \nZły login lub hasło", Toast.LENGTH_LONG).show();
        } else {
            if (checkBoxRemember.isChecked()) {
                Functions.saveUserData(this, UserModel.getInstance().getCurrentUser());
            }
            startActivity(new Intent(this, MainMenuActivity.class));
        }

    }
}
