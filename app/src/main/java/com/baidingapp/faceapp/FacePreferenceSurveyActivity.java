package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FacePreferenceSurveyActivity extends AppCompatActivity {

    // The URL is used to test code
    private final String imageUrl2 = "http://bus.sysu.edu.cn/uploads/Head/201101/201101070814308595.jpg";

    private ImageView mFaceImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_preference_survey);


        // The URL is used to test Picasso
        String imageUrl1 = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";
        // Show the initial image
        mFaceImageView = (ImageView) findViewById(R.id.face_image_preference_survey);
        updateFaceImage(imageUrl1);


        Button mLikeButton = (Button) findViewById(R.id.action_like);
        Button mDislikeButton = (Button) findViewById(R.id.action_dislike);

        mLikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateFaceImage(imageUrl2);
            }
        });

        mDislikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateFaceImage(imageUrl2);
            }
        });
    }


    private void updateFaceImage(String imageUrl) {
        Picasso.with(this).load(imageUrl)
                // show the resource image while downloading images
                .placeholder(R.drawable.face_image)
                // show the resource image if there is an error in downloading images
                .error(R.drawable.face_image)
                .into(mFaceImageView);
    }
}
