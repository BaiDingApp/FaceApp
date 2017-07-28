package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class OutputRateFaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_rate_face);

        ImageView mFaceImageView = (ImageView) findViewById(R.id.action_upload_photo_output_rate);

        // The URL is used to test Picasso
        String imageUrl = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";

        Picasso.with(this).load(imageUrl)
                // show the resource image while downloading images
                .placeholder(R.drawable.face_image)
                // show the resource image if there is an error in downloading images
                .error(R.drawable.face_image)
                .into(mFaceImageView);
    }
}
