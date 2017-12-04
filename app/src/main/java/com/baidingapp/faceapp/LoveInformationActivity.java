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

public class LoveInformationActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private String loveInfoObjectId;

    private int mIncomeYearPosition;
    private int mEduOverseaPosition;

    private Spinner mIncomeYearSpinner;
    private Spinner mEduOverseaSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_love_information);


        // onClick IncomeYear spinner
        mIncomeYearSpinner = (Spinner) findViewById(R.id.action_love_income_year);
        ArrayAdapter mIncomeYearAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_income_year, android.R.layout.simple_spinner_item);
        mIncomeYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mIncomeYearSpinner.setAdapter(mIncomeYearAdapter);
        mIncomeYearSpinner.setOnItemSelectedListener(this);


        // onClick EduOversea spinner
        mEduOverseaSpinner = (Spinner) findViewById(R.id.action_love_edu_oversea);
        ArrayAdapter mEduOverseaAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mEduOverseaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEduOverseaSpinner.setAdapter(mEduOverseaAdapter);
        mEduOverseaSpinner.setOnItemSelectedListener(this);


        // Create the profile by using info saved in SharedPreference when onCreate
        createProfileUsingSavedData();


        // onClick SUBMIT button
        Button mSaveButton = (Button) findViewById(R.id.action_submit_love_information);
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
            case R.id.action_love_income_year:
                mIncomeYearPosition = position;
                break;

            case R.id.action_love_edu_oversea:
                mEduOverseaPosition = position;
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Create the profile by using info saved in SharedPreference when onCreate
    private void createProfileUsingSavedData() {
        // IncomeYear
        mIncomeYearPosition = MyInfoPreference.getStoredIncomeYear(LoveInformationActivity.this);
        mIncomeYearSpinner.setSelection(mIncomeYearPosition);

        // EduOversea
        mEduOverseaPosition = MyInfoPreference.getStoredEduOversea(LoveInformationActivity.this);
        mEduOverseaSpinner.setSelection(mEduOverseaPosition);
    }


    // Save data to SharedPreference
    private void saveDataToSharedPreference() {
        MyInfoPreference.setStoredIncomeYear(LoveInformationActivity.this, mIncomeYearPosition);
        MyInfoPreference.setStoredEduOversea(LoveInformationActivity.this, mEduOverseaPosition);
    }


    // Save data to LeanCloud
    private void saveDataToLeanCloud() {
        loveInfoObjectId = MyInfoPreference.getStoredLoveInfoObjectId(LoveInformationActivity.this);

        if (loveInfoObjectId != null) {
            final AVObject mLoveInfo = AVObject.createWithoutData("LoveInfo", loveInfoObjectId);

            mLoveInfo.put("incomeYear", mIncomeYearPosition);
            mLoveInfo.put("eduOversea", mEduOverseaPosition);

            mLoveInfo.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        Toast.makeText(LoveInformationActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoveInformationActivity.this, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            final AVObject mLoveInfo = new AVObject("LoveInfo");

            // Create the pointer that points to _User
            mLoveInfo.put("username", AVUser.getCurrentUser().getUsername());
            mLoveInfo.put("userId", AVUser.getCurrentUser());

            mLoveInfo.put("incomeYear", mIncomeYearPosition);
            mLoveInfo.put("eduOversea", mEduOverseaPosition);

            mLoveInfo.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        // Save the objectId of LoveInfo to SharedPreference
                        loveInfoObjectId = mLoveInfo.getObjectId();
                        MyInfoPreference.setStoredLoveInfoObjectId(LoveInformationActivity.this, loveInfoObjectId);

                        Toast.makeText(LoveInformationActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoveInformationActivity.this, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }  // End of "Save data to LeanCloud"
}
