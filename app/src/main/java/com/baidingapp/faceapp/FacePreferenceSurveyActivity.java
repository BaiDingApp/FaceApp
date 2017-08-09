package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class FacePreferenceSurveyActivity extends AppCompatActivity {

    private ImageView mFaceImageView;
    private ArrayList<String> imageUrlList;
    private int mImageUrlIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_preference_survey);


        // Just for testing
        imageUrlList = new ArrayList<>();
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744866697126501620226.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744858711401934485062.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744893747816378981865.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744881214642035467167.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744868010661201111709.jpg");
        imageUrlList.add("http://bus.sysu.edu.cn/uploads/Head/201101/201101070814308595.jpg");


        // Show the initial image
        mFaceImageView = (ImageView) findViewById(R.id.face_image_preference_survey);
        GlideApp.with(this).load(imageUrlList.get(mImageUrlIndex))
                // show the resource image while downloading images
                .placeholder(R.drawable.face_image)
                // show the resource image if there is an error in downloading images
                .error(R.drawable.face_image)
                .into(mFaceImageView);


        Button mLikeButton = (Button) findViewById(R.id.action_like);
        Button mDislikeButton = (Button) findViewById(R.id.action_dislike);

        mLikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateFaceImage();
            }
        });

        mDislikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateFaceImage();
            }
        });
    }


    private void updateFaceImage() {
        mImageUrlIndex = mImageUrlIndex + 1;

        String imageUrl = imageUrlList.get(mImageUrlIndex);

        GlideApp.with(this).load(imageUrl)
                // show the resource image while downloading images
                .placeholder(R.drawable.face_image)
                // show the resource image if there is an error in downloading images
                .error(R.drawable.face_image)
                .into(mFaceImageView);
    }
}
