package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BasicInformationSingleActivity extends AppCompatActivity {

    private boolean mIsSingle=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_single);

        // onClick SUBMIT button
        // Deliver the mIsSingle variable to MainActivity
        Button mSubmitButton = (Button) findViewById(R.id.action_submit_basic_info_single);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.newIntent(BasicInformationSingleActivity.this, mIsSingle);
                startActivity(intent);
                finish();
            }
        });

    }
}
