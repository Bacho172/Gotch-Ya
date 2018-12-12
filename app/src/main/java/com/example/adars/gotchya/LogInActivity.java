package com.example.adars.gotchya;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class LogInActivity extends AppCompatActivity {

    //TBE
    private static final int RC_SIGN_IN = 9001;
    /*
     private EditText editTextLogin;
     private EditText editTextPassword;
     private CheckBox checkBoxRemember;
     */
    private ImageButton imageButtonLogOut;
    private SignInButton signInButtonGoogle;
    private Button check;
    private TextView textViewSignedUser;
    private GoogleSignInAccount account;
    private GoogleSignInAccount acc;
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        check = findViewById(R.id.button_check);
        // editTextLogin = findViewById(R.id.editTextLogin);
        //editTextPassword = findViewById(R.id.editTextPassword);
        //checkBoxRemember = findViewById(R.id.checkBoxRemember);
        imageButtonLogOut = findViewById(R.id.imageButtonLogOut);
        imageButtonLogOut.setOnClickListener(l -> imageButtonLogInClick());
        signInButtonGoogle = findViewById(R.id.sign_out_button);
        signInButtonGoogle.setSize(SignInButton.SIZE_STANDARD);
        textViewSignedUser = findViewById(R.id.textViewSignedUser);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = acc.getEmail();
                String mac_address = "123";
                String model = "iphone";
                String name = "X20";
                String system = "Kali linux";
                URL url = null;
                try {
                    url = new URL("https://gotch-ya.herokuapp.com/api/devices");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection client = (HttpURLConnection) url.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                HttpURLConnection client = null;
                try {
                    client.setRequestMethod("POST");
                } catch (ProtocolException e) {
                    e.printStackTrace();
                }
                client.setRequestProperty("gmail", email);
                client.setRequestProperty("mac_address", mac_address);
                client.setRequestProperty("model", model);
                client.setRequestProperty("name", name);
                client.setRequestProperty("system", system);
                client.setDoOutput(true);
                OutputStream outputPost = null;
                try {
                    outputPost = new BufferedOutputStream(client.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //  writeStream(outputPost);
                try {
                    outputPost.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputPost.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        GoogleSignInOptions gso;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        account = GoogleSignIn.getLastSignedInAccount(this);

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
            imageButtonLogOut.setVisibility(View.VISIBLE);
            signInButtonGoogle.setVisibility(View.GONE);
            textViewSignedUser.setText(account.getEmail());
        } else {
            imageButtonLogOut.setVisibility(View.GONE);
            signInButtonGoogle.setVisibility(View.VISIBLE);
            textViewSignedUser.setText(" ");
            Toast.makeText(getApplicationContext(), "Log out ", Toast.LENGTH_LONG).show();
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

        mGoogleSignInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateUI(null);
            }
        });

        /*
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
*/
    }
}
