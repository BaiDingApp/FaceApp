package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyInformationSingleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information_single);

        Button mBasicInformationButton = (Button) findViewById(R.id.action_basic_information_single);
        mBasicInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformationSingleActivity.this, BasicInformationSingleActivity.class);
                startActivity(intent);
            }
        });

        Button mFaceInformationButton = (Button) findViewById(R.id.action_face_information);
        mFaceInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformationSingleActivity.this, FaceInformationActivity.class);
                startActivity(intent);
            }
        });

        Button mSelfHateButton = (Button) findViewById(R.id.action_self_hate);
        mSelfHateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformationSingleActivity.this, SelfHateInformationActivity.class);
                startActivity(intent);
            }
        });

        Button mSelfLoveButton = (Button) findViewById(R.id.action_self_love);
        mSelfLoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformationSingleActivity.this, SelfLoveInformationActivity.class);
                startActivity(intent);
            }
        });

        Button mEliminationRulesButton = (Button) findViewById(R.id.action_elimination_rules);
        mEliminationRulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInformationSingleActivity.this, EliminationRulesActivity.class);
                startActivity(intent);
            }
        });
    }
}
