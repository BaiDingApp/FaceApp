package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.MyInfoPreference;

public class HateInformationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private String hateInfoObjectId;

    private int mAgeGreaterPosition;
    private int mAgeLessPosition;
    private int mHeightGreaterPosition;
    private int mHeightLessPosition;

    private Spinner mAgeGreaterSpinner;
    private Spinner mAgeLessSpinner;
    private Spinner mHeightGreaterSpinner;
    private Spinner mHeightLessSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hate_information);


        // onClick AgeGreater spinner
        mAgeGreaterSpinner = (Spinner) findViewById(R.id.action_hate_age_greater);
        ArrayAdapter mAgeGreaterAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_age_difference, android.R.layout.simple_spinner_item);
        mAgeGreaterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAgeGreaterSpinner.setAdapter(mAgeGreaterAdapter);
        mAgeGreaterSpinner.setOnItemSelectedListener(this);

        // onClick AgeLess spinner
        mAgeLessSpinner = (Spinner) findViewById(R.id.action_hate_age_less);
        ArrayAdapter mAgeLessAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_age_difference, android.R.layout.simple_spinner_item);
        mAgeLessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAgeLessSpinner.setAdapter(mAgeLessAdapter);
        mAgeLessSpinner.setOnItemSelectedListener(this);

        // onClick HeightGreater spinner
        mHeightGreaterSpinner = (Spinner) findViewById(R.id.action_hate_height_greater);
        ArrayAdapter mHeightGreaterAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_height, android.R.layout.simple_spinner_item);
        mHeightGreaterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHeightGreaterSpinner.setAdapter(mHeightGreaterAdapter);
        mHeightGreaterSpinner.setOnItemSelectedListener(this);

        // onClick HeightLess spinner
        mHeightLessSpinner = (Spinner) findViewById(R.id.action_hate_height_less);
        ArrayAdapter mHeightLessAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_height, android.R.layout.simple_spinner_item);
        mHeightLessAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHeightLessSpinner.setAdapter(mHeightLessAdapter);
        mHeightLessSpinner.setOnItemSelectedListener(this);


        // Create the profile by using info saved in SharedPreference when onCreate
        createProfileUsingSavedData();


        // onClick SUBMIT button
        Button mSaveButton = (Button) findViewById(R.id.action_submit_hate_information);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save data to SharedPreference
                saveDataToSharedPreference();

                // Save data to LeanCloud
                saveDataToLeanCloud();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.action_hate_age_greater:
                mAgeGreaterPosition = position;
                break;

            case R.id.action_hate_age_less:
                mAgeLessPosition = position;
                break;

            case R.id.action_hate_height_greater:
                mHeightGreaterPosition = position;
                break;

            case R.id.action_hate_height_less:
                mHeightLessPosition = position;
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Create the profile by using info saved in SharedPreference when onCreate
    private void createProfileUsingSavedData() {
        // AgeGreater
        mAgeGreaterPosition = MyInfoPreference.getStoredAgeGreater(HateInformationActivity.this);
        mAgeGreaterSpinner.setSelection(mAgeGreaterPosition);

        // AgeLess
        mAgeLessPosition = MyInfoPreference.getStoredAgeLess(HateInformationActivity.this);
        mAgeLessSpinner.setSelection(mAgeLessPosition);

        // HeightGreater
        mHeightGreaterPosition = MyInfoPreference.getStoredHeightGreater(HateInformationActivity.this);
        mHeightGreaterSpinner.setSelection(mHeightGreaterPosition);

        // HeightLess
        mHeightLessPosition = MyInfoPreference.getStoredHeightLess(HateInformationActivity.this);
        mHeightLessSpinner.setSelection(mHeightLessPosition);
    }


    // Save data to SharedPreference
    private void saveDataToSharedPreference() {
        MyInfoPreference.setStoredAgeGreater(HateInformationActivity.this, mAgeGreaterPosition);
        MyInfoPreference.setStoredAgeLess(HateInformationActivity.this, mAgeLessPosition);
        MyInfoPreference.setStoredHeightGreater(HateInformationActivity.this, mHeightGreaterPosition);
        MyInfoPreference.setStoredHeightLess(HateInformationActivity.this, mHeightLessPosition);
    }


    // Save data to LeanCloud
    private void saveDataToLeanCloud() {
        hateInfoObjectId = MyInfoPreference.getStoredHateInfoObjectId(HateInformationActivity.this);

        if (hateInfoObjectId != null) {
            final AVObject mHateInfo = AVObject.createWithoutData("HateInfo", hateInfoObjectId);

            mHateInfo.put("ageGreater", mAgeGreaterPosition);
            mHateInfo.put("ageLess", mAgeLessPosition);
            mHateInfo.put("heightGreater", mHeightGreaterPosition);
            mHateInfo.put("heightLess", mHeightLessPosition);

            mHateInfo.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Toast.makeText(HateInformationActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HateInformationActivity.this, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            final AVObject mHateInfo = new AVObject("HateInfo");

            // Create the pointer that points to _User
            mHateInfo.put("username", AVUser.getCurrentUser().getUsername());
            mHateInfo.put("userId", AVUser.getCurrentUser());

            mHateInfo.put("ageGreater", mAgeGreaterPosition);
            mHateInfo.put("ageLess", mAgeLessPosition);
            mHateInfo.put("heightGreater", mHeightGreaterPosition);
            mHateInfo.put("heightLess", mHeightLessPosition);

            mHateInfo.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        // Save the objectId of HateInfo to SharedPreference
                        hateInfoObjectId = mHateInfo.getObjectId();
                        MyInfoPreference.setStoredHateInfoObjectId(HateInformationActivity.this, hateInfoObjectId);

                        Toast.makeText(HateInformationActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(HateInformationActivity.this, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }  // End of "Save data to LeanCloud"
}
