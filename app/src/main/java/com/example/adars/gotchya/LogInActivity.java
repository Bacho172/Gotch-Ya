package com.example.adars.gotchya;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LogInActivity extends AppCompatActivity {

    //TBE
    private static final int RC_SIGN_IN = 9001;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private CheckBox checkBoxRemember;
    private ImageButton imageButtonLogIn;
    private SignInButton signInButtonGoogle;
    private Button check;
    private GoogleSignInAccount acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        check = findViewById(R.id.button_check);
        editTextLogin = findViewById(R.id.editTextLogin);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);
        imageButtonLogIn = findViewById(R.id.imageButtonLogIn);
        imageButtonLogIn.setOnClickListener(l -> imageButtonLogInClick());
        GoogleSignInOptions gso;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acc=GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                Toast.makeText(LogInActivity.this, "email:" + acc.getEmail(), Toast.LENGTH_SHORT).show();
            }
        });
        signInButtonGoogle = findViewById(R.id.sign_in_button);
        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        signInButtonGoogle.setSize(SignInButton.SIZE_STANDARD);

    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        int a = 7;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            Toast.makeText(getApplicationContext(), "email:" + account.getEmail() + " id token:" + account.getIdToken(), Toast.LENGTH_LONG).show();
            signInButtonGoogle.setVisibility(View.GONE);
        } else {
            signInButtonGoogle.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(), "Sign in google error", Toast.LENGTH_LONG).show();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            updateUI(null);
        }
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
