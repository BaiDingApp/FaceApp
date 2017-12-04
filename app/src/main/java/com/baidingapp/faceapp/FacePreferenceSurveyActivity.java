package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.baidingapp.faceapp.helper.ImageHelper;
import com.baidingapp.faceapp.helper.MyInfoPreference;

import java.util.ArrayList;

public class FacePreferenceSurveyActivity extends AppCompatActivity {

    private ImageView mFaceImageView;
    private ArrayList<AVObject> imageUrlList = new ArrayList<>();
    private int mImageUrlIndex = 0;

    private Button mLikeButton;
    private Button mDislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_preference_survey);


        // Just for testing
        getUrlOfRateFacePhotos();

/*
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744866697126501620226.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744858711401934485062.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744893747816378981865.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744881214642035467167.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744868010661201111709.jpg");
        imageUrlList.add("http://bus.sysu.edu.cn/uploads/Head/201101/201101070814308595.jpg");
*/

        // Show the initial image
        mFaceImageView = (ImageView) findViewById(R.id.face_image_preference_survey);
/*
        GlideApp.with(this).load(imageUrlList.get(mImageUrlIndex))
                // show the resource image while downloading images
                .placeholder(R.drawable.face_image)
                // show the resource image if there is an error in downloading images
                .error(R.drawable.face_image)
                .into(mFaceImageView);
*/

        // on Click LIKE and DISLIKE Buttons, then Save the Results to LeanCloud
        mLikeButton = (Button) findViewById(R.id.action_like);
        mDislikeButton = (Button) findViewById(R.id.action_dislike);

        // Like = 1; Dislike = 0
        mLikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveFacePreferenceToLeanCloud(1);

                if ((mImageUrlIndex+1) < imageUrlList.size()) {
                    updateFaceImage();
                } else {
                    Toast.makeText(FacePreferenceSurveyActivity.this, "没有新的照片了，明天再来吧", Toast.LENGTH_SHORT).show();
                    mLikeButton.setEnabled(false);
                    mDislikeButton.setEnabled(false);
                }
            }
        });

        mDislikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveFacePreferenceToLeanCloud(0);

                if ((mImageUrlIndex+1) < imageUrlList.size()) {
                    updateFaceImage();
                } else {
                    Toast.makeText(FacePreferenceSurveyActivity.this, "没有新的照片了，明天再来吧", Toast.LENGTH_SHORT).show();
                    mLikeButton.setEnabled(false);
                    mDislikeButton.setEnabled(false);
                }
            }
        });
    }


    // Update the Face Image after Click the Buttons
    private void updateFaceImage() {
        mImageUrlIndex = mImageUrlIndex + 1;
/*
        String imageUrl = imageUrlList.get(mImageUrlIndex);

        GlideApp.with(this).load(imageUrl)
                // show the resource image while downloading images
                .placeholder(R.drawable.face_image)
                // show the resource image if there is an error in downloading images
                .error(R.drawable.face_image)
                .into(mFaceImageView);
*/
        ImageHelper.ImageLoad(FacePreferenceSurveyActivity.this, imageUrlList.get(mImageUrlIndex).getString("photoUrl"), mFaceImageView);

    }


    // Get photoUrl in the RateFacePhoto Table from LeanCloud
    // Use CQL query method
    // 1. Users cannot see the photos they saw before;
    // 2. They can see their own photos once
    private void getUrlOfRateFacePhotos() {
        // String photoNotRated = "select * from RateFacePhoto where objectId !=  '599115cb570c35006b684d5c' ";
        String currUsername = AVUser.getCurrentUser().getUsername();
        int currGender = MyInfoPreference.getStoredGender(FacePreferenceSurveyActivity.this);
        String photoNotRated = "select * from RateFacePhoto where gender != ? and username != (select usernameRated from FacePreferenceSurvey where usernameRating = ? limit 1000) limit 100";

        AVQuery.doCloudQueryInBackground(photoNotRated, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                for (AVObject photo : (avCloudQueryResult.getResults())) {
                    imageUrlList.add(photo);
                }

                // Initialize the ImageView
                if (imageUrlList.size() != 0) {
                    ImageHelper.ImageLoad(FacePreferenceSurveyActivity.this, imageUrlList.get(mImageUrlIndex).getString("photoUrl"), mFaceImageView);
                } else {
                    ImageHelper.ImageLoad(FacePreferenceSurveyActivity.this, null, mFaceImageView);
                    Toast.makeText(FacePreferenceSurveyActivity.this, "没有新的照片了，明天再来吧", Toast.LENGTH_SHORT).show();
                    mLikeButton.setEnabled(false);
                    mDislikeButton.setEnabled(false);
                }
            }
        }, currGender, currUsername);

    }


    // Save Face Preference Results (yes or no) to LeanCloud
    private void saveFacePreferenceToLeanCloud(int like) {
        // Get mUsernameRating and mUsernameRated
        String mUsernameRating = AVUser.getCurrentUser().getUsername();
        String mUsernameRated  = imageUrlList.get(mImageUrlIndex).getString("username");

        // Save Results
        AVObject mFacePreferenceResult = new AVObject("FacePreferenceSurvey");
        mFacePreferenceResult.put("usernameRating", mUsernameRating);
        mFacePreferenceResult.put("usernameRated", mUsernameRated);
        mFacePreferenceResult.put("userIdRating", AVUser.getCurrentUser());
        mFacePreferenceResult.put("photoIdRated", imageUrlList.get(mImageUrlIndex));

        mFacePreferenceResult.put("like", like);
        mFacePreferenceResult.saveInBackground();
    }

























}