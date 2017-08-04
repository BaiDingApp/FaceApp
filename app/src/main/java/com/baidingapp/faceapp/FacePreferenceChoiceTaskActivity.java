package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.baidingapp.faceapp.helper.ImageHelper;

import java.util.ArrayList;

public class FacePreferenceChoiceTaskActivity extends AppCompatActivity {

    private ImageView mFirstFaceImage;
    private ImageView mSecondFaceImage;

    private String mFirstImageUrl;
    private String mSecondImageUrl;

    private int mCurrentIndex = 1;
    private int mFirstPrevious = 0;
    private int mSecondPrevious = 1;
    private int[] mWinnerPhotoIndex = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int[] mLoserPhotoIndex = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int mClickNumber = 1;  // Should be deleted if mCurrentIndex is redefined later

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_preference_choice_task);


        // Just for testing
        final ArrayList<String> imageUrlList = new ArrayList<>();
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744866697126501620226.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744858711401934485062.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744893747816378981865.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744881214642035467167.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744868010661201111709.jpg");


        // Initialize FIRST ImageView
        mFirstFaceImage = (ImageView) findViewById(R.id.show_first_photo);
        mFirstImageUrl = imageUrlList.get(0);
        ImageHelper.ImageLoad(FacePreferenceChoiceTaskActivity.this, mFirstImageUrl, mFirstFaceImage);

        // onClick FIRST ImageView
        mFirstFaceImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCurrentIndex = (mCurrentIndex + 1) % imageUrlList.size();
                mFirstImageUrl = imageUrlList.get(mCurrentIndex);
                ImageHelper.ImageLoad(FacePreferenceChoiceTaskActivity.this, mFirstImageUrl, mFirstFaceImage);

                mWinnerPhotoIndex[mClickNumber-1] = mSecondPrevious;
                mLoserPhotoIndex[mClickNumber-1] = mFirstPrevious;
                mClickNumber = mClickNumber + 1;
                mFirstPrevious = mClickNumber;
            }
        });


        // Initialize SECOND ImageView
        mSecondFaceImage = (ImageView) findViewById(R.id.show_second_photo);
        mSecondImageUrl = imageUrlList.get(1);
        ImageHelper.ImageLoad(FacePreferenceChoiceTaskActivity.this, mSecondImageUrl, mSecondFaceImage);

        // onClick SECOND ImageView
        mSecondFaceImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mCurrentIndex = (mCurrentIndex + 1) % imageUrlList.size();
                mSecondImageUrl = imageUrlList.get(mCurrentIndex);
                ImageHelper.ImageLoad(FacePreferenceChoiceTaskActivity.this, mSecondImageUrl, mSecondFaceImage);

                mWinnerPhotoIndex[mClickNumber-1] = mFirstPrevious;
                mLoserPhotoIndex[mClickNumber-1] = mSecondPrevious;
                mClickNumber = mClickNumber + 1;
                mSecondPrevious = mClickNumber;
            }
        });


        // Save the data into the DataBase
        Button mStopPreferenceSurveyButton = (Button) findViewById(R.id.action_stop_face_choice_task);
        mStopPreferenceSurveyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*

                AVObject mRemovedPhotoIndex = new AVObject("FaceChoiceTask");
                String currentUsername = AVUser.getCurrentUser().getUsername();
                mRemovedPhotoIndex.put("username", currentUsername);
                mRemovedPhotoIndex.put("winnerphotoindex", mWinnerPhotoIndex);
                mRemovedPhotoIndex.put("loserphotoindex", mLoserPhotoIndex);
                mRemovedPhotoIndex.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            Log.d("saved","success!");
                        } else {
                            // 失败的话，请检查网络环境以及 SDK 配置是否正确
                        }
                    }
                });

                finish();

                */
            }
        });
    }
}

















