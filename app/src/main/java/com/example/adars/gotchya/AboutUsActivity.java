package com.example.adars.gotchya;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AboutUsActivity extends AppCompatActivity {

    private TextView textViewLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        textViewLink = findViewById(R.id.textViewLink);
        //textViewLink.setMovementMethod(LinkMovementMethod.getInstance());
        textViewLink.setOnClickListener(l ->
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(textViewLink.getText().toString())))
        );
    }
}
