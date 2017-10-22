package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

public class LauncherPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher_page);

        // If the user did not LOGOUT, then directly launch on the MainActivity
        if (AVUser.getCurrentUser() != null) {
            Intent intent = new Intent(LauncherPageActivity.this, MainActivity.class);
            startActivity(intent);
            LauncherPageActivity.this.finish();
        }

        // onClick REGISTER button
        Button mRegisterButton = (Button) findViewById(R.id.action_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherPageActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // onClick LOGIN button
        Button mLoginButton = (Button) findViewById(R.id.action_login);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherPageActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // onCLick the Privacy and Service TextView
        TextView mPrivacyServiceTerms = (TextView) findViewById(R.id.privacy_service_terms);
        mPrivacyServiceTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherPageActivity.this, PrivacyServiceTermsActivity.class);
                startActivity(intent);
            }
        });
    }
}
