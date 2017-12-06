package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class ResumePreferenceSurveyActivity extends AppCompatActivity {

    private ArrayList<AVObject> mResumeList = new ArrayList<>();
    private int mResumeIndex = 0;
    private boolean mInitializeIndex = true;

    private TextView mBirthdayText;
    private TextView mHeightText;
    private TextView mWeightText;
    private TextView mEducationText;
    private TextView mOccupationText;
    private TextView mBirthPlaceText;
    private TextView mWorkPlaceText;
    private TextView mOverseaText;
    private TextView mReligionText;
    private TextView mPetText;
    private TextView mIncomeText;
    private TextView mMaritalHistoryText;
    private TextView mHasKidsText;
    private TextView mWantKidsText;
    private TextView mHasHouseText;
    private TextView mHasCarText;
    private TextView mLiveWithParentsText;
    private TextView mFaceLiftText;
    private TextView mSmokingText;
    private TextView mDrinkingText;
    private TextView mGamblingText;

    private Button mLikeButton;
    private Button mDislikeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_preference_survey);


        getResumeList();


        // Get ID for All TextViews
        mBirthdayText = (TextView) findViewById(R.id.action_resume_preference_birthday);
        mHeightText = (TextView) findViewById(R.id.action_resume_preference_height);
        mWeightText = (TextView) findViewById(R.id.action_resume_preference_weight);
        mEducationText = (TextView) findViewById(R.id.action_resume_preference_education);
        mOccupationText = (TextView) findViewById(R.id.action_resume_preference_occupation);
        mBirthPlaceText = (TextView) findViewById(R.id.action_resume_preference_birth_place);
        mWorkPlaceText = (TextView) findViewById(R.id.action_resume_preference_work_place);
        mOverseaText = (TextView) findViewById(R.id.action_resume_preference_oversea);
        mReligionText = (TextView) findViewById(R.id.action_resume_preference_religion);
        mPetText = (TextView) findViewById(R.id.action_resume_preference_pet);
        mIncomeText = (TextView) findViewById(R.id.action_resume_preference_income);
        mMaritalHistoryText = (TextView) findViewById(R.id.action_resume_preference_marital_history);
        mHasKidsText = (TextView) findViewById(R.id.action_resume_preference_has_kids);
        mWantKidsText = (TextView) findViewById(R.id.action_resume_preference_has_kids_after_marriage);
        mHasHouseText = (TextView) findViewById(R.id.action_resume_preference_has_house);
        mHasCarText = (TextView) findViewById(R.id.action_resume_preference_has_car);
        mLiveWithParentsText = (TextView) findViewById(R.id.action_resume_preference_live_with_parents_after_marriage);
        mFaceLiftText = (TextView) findViewById(R.id.action_resume_preference_face_lift);
        mSmokingText = (TextView) findViewById(R.id.action_resume_preference_smoking);
        mDrinkingText = (TextView) findViewById(R.id.action_resume_preference_drinking);
        mGamblingText = (TextView) findViewById(R.id.action_resume_preference_gambling);


        // Set Texts for All TextViews


        // on Click LIKE and DISLIKE Buttons, then Save the Results to LeanCloud
        mLikeButton = (Button) findViewById(R.id.action_like_resume);
        mDislikeButton = (Button) findViewById(R.id.action_dislike_resume);

        // Like = 1; Dislike = 0
        mLikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveResumePreferenceToLeanCloud(1);

                if ((mResumeIndex+1) < mResumeList.size()) {
                    updateResumeProfile();
                } else {
                    Toast.makeText(ResumePreferenceSurveyActivity.this, "没有新的简历了，明天再来吧", Toast.LENGTH_SHORT).show();
                    mLikeButton.setEnabled(false);
                    mDislikeButton.setEnabled(false);
                }
            }
        });

        mDislikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveResumePreferenceToLeanCloud(0);

                if ((mResumeIndex+1) < mResumeList.size()) {
                    updateResumeProfile();
                } else {
                    Toast.makeText(ResumePreferenceSurveyActivity.this, "没有新的简历了，明天再来吧", Toast.LENGTH_SHORT).show();
                    mLikeButton.setEnabled(false);
                    mDislikeButton.setEnabled(false);
                }
            }
        });

    }


    // Update the Resume Profile after Click the Buttons
    private void updateResumeProfile() {
        if (mInitializeIndex) {
            mInitializeIndex = false;
        }
        else {
            mResumeIndex = mResumeIndex + 1;
        }

        int mInt = mResumeList.get(mResumeIndex).getInt("educationLevel");
        String mString =  getResources().getStringArray(R.array.spinner_education_levels)[mInt];
        mEducationText.setText(mString);
    }


    // Get Resume Info in the BasicInfo and MoreInfo Tables from LeanCloud
    // Use CQL query method
    // 1. Users cannot see the resumes they saw before;
    // 2. They can see their own resume once
    private void getResumeList() {
        String currUsername = AVUser.getCurrentUser().getUsername();
        int currGender = MyInfoPreference.getStoredGender(ResumePreferenceSurveyActivity.this);
        int currGenderInv = 2;
        if (currGender == 2) {
            currGenderInv = 1;
        }

        String resumeNotRated = "select * from BasicInfo where gender = ? and username != (select usernameRated from ResumePreferenceSurvey where usernameRating = ? limit 1000) limit 100";

        AVQuery.doCloudQueryInBackground(resumeNotRated, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                for (AVObject resume : (avCloudQueryResult.getResults())) {
                    mResumeList.add(resume);
                }

                // Initialize the ImageView
                if (mResumeList.size() != 0) {
                    updateResumeProfile();
                }
                else {
                    Toast.makeText(ResumePreferenceSurveyActivity.this, "没有新的简历了，明天再来吧", Toast.LENGTH_SHORT).show();
                    mLikeButton.setEnabled(false);
                    mDislikeButton.setEnabled(false);
                }
            }
        }, currGenderInv, currUsername);
    }


    // Save Resume Preference Results (yes or no) to LeanCloud
    private void saveResumePreferenceToLeanCloud(int like) {
        // Get mUsernameRating and mUsernameRated
        String mUsernameRating = AVUser.getCurrentUser().getUsername();
        String mUsernameRated  = mResumeList.get(mResumeIndex).getString("username");

        // Save Results
        AVObject mResumePreferenceResult = new AVObject("ResumePreferenceSurvey");
        mResumePreferenceResult.put("usernameRating", mUsernameRating);
        mResumePreferenceResult.put("usernameRated", mUsernameRated);
        // mResumePreferenceResult.put("userIdRating", AVUser.getCurrentUser());
        // mResumePreferenceResult.put("photoIdRated", mResumeList.get(mResumeIndex));

        mResumePreferenceResult.put("like", like);
        mResumePreferenceResult.saveInBackground();
    }
}
