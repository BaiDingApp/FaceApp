package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class OutputRateFaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_rate_face);

        ImageView mFaceImageView = (ImageView) findViewById(R.id.action_upload_photo_output_rate);
        mFaceImageView.setImageResource(R.drawable.face_image);
    }
}
