package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        // onClick the "About App"
        TextView mAboutApp = (TextView) findViewById(R.id.action_about_app);
        mAboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, AboutAppActivity.class);
                startActivity(intent);
            }
        });


        // onClick the "New User Orientation"
        TextView mNewUserOrientation = (TextView) findViewById(R.id.action_new_user_orientation);
        mNewUserOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, NewUserOrientationActivity.class);
                startActivity(intent);
            }
        });


        // onClick the "Suggestion and Feedback"
        TextView mSuggestionFeedback = (TextView) findViewById(R.id.action_suggestion_feedback);
        mSuggestionFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO
                Toast.makeText(SettingsActivity.this, "TODO", Toast.LENGTH_SHORT).show();
            }
        });


        // onClick the Logout Button
        Button mLogoutButton = (Button) findViewById(R.id.action_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser.getCurrentUser().logOut();
                finish();
                Intent intent = new Intent(SettingsActivity.this, LauncherPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}
