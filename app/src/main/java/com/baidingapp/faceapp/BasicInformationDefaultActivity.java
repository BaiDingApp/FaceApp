package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.MyInfoPreference;

public class BasicInformationDefaultActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private int mEducationPosition;
    private int mOccupationPosition;
    private int mBirthPlacePosition;
    private int mWorkPlacePosition;
    private int mOverseaPosition;
    private int mSinglePosition;
    private int mReligionPosition;
    private int mPetPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_default);


        // onClick Education spinner
        Spinner mEducationSpinner = (Spinner) findViewById(R.id.action_basic_education_default);
        ArrayAdapter mEducationAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_education_levels, android.R.layout.simple_spinner_item);
        mEducationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEducationSpinner.setAdapter(mEducationAdapter);
        mEducationSpinner.setOnItemSelectedListener(this);


        // onClick Occupation spinner
        Spinner mOccupationSpinner = (Spinner) findViewById(R.id.action_basic_occupation_default);
        ArrayAdapter mOccupationAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_occupations, android.R.layout.simple_spinner_item);
        mOccupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOccupationSpinner.setAdapter(mOccupationAdapter);
        mOccupationSpinner.setOnItemSelectedListener(this);


        // onClick BirthPlace spinner
        Spinner mBirthPlaceSpinner = (Spinner) findViewById(R.id.action_basic_birth_place_default);
        ArrayAdapter mBirthPlaceAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_place, android.R.layout.simple_spinner_item);
        mBirthPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBirthPlaceSpinner.setAdapter(mBirthPlaceAdapter);
        mBirthPlaceSpinner.setOnItemSelectedListener(this);


        // onClick WorkPlace spinner
        Spinner mWorkPlaceSpinner = (Spinner) findViewById(R.id.action_basic_work_place_default);
        ArrayAdapter mWorkPlaceAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_place, android.R.layout.simple_spinner_item);
        mWorkPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWorkPlaceSpinner.setAdapter(mWorkPlaceAdapter);
        mWorkPlaceSpinner.setOnItemSelectedListener(this);


        // onClick Oversea spinner
        Spinner mOverseaSpinner = (Spinner) findViewById(R.id.action_basic_oversea_default);
        ArrayAdapter mOverseaAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mOverseaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOverseaSpinner.setAdapter(mOverseaAdapter);
        mOverseaSpinner.setOnItemSelectedListener(this);


        // onClick Single spinner
        Spinner mSingleSpinner = (Spinner) findViewById(R.id.action_basic_single_default);
        ArrayAdapter mSingleAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mSingleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSingleSpinner.setAdapter(mSingleAdapter);
        mSingleSpinner.setOnItemSelectedListener(this);


        // onClick Religion spinner
        Spinner mReligionSpinner = (Spinner) findViewById(R.id.action_basic_religion_default);
        ArrayAdapter mReligionAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mReligionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReligionSpinner.setAdapter(mReligionAdapter);
        mReligionSpinner.setOnItemSelectedListener(this);


        // onClick Pet spinner
        Spinner mPetSpinner = (Spinner) findViewById(R.id.action_basic_pet_default);
        ArrayAdapter mPetAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mPetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPetSpinner.setAdapter(mPetAdapter);
        mPetSpinner.setOnItemSelectedListener(this);


        Button mSubmitButton = (Button) findViewById(R.id.action_submit_basic_info_default);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save data to SharedPreference
                saveDataToSharedPreference();

                // Save data to LeanCloud
                saveDataToLeanCloud();

                // TODO
                // If the former context is RegisterActivity, then finish the current context,
                //     and start the MainActivity.
                Intent intent = new Intent(BasicInformationDefaultActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.action_basic_education_default:
                mEducationPosition = position;
                break;

            case R.id.action_basic_occupation_default:
                mOccupationPosition = position;
                break;

            case R.id.action_basic_birth_place_default:
                mBirthPlacePosition = position;
                break;

            case R.id.action_basic_work_place_default:
                mWorkPlacePosition = position;
                break;

            case R.id.action_basic_oversea_default:
                mOverseaPosition = position;
                break;

            case R.id.action_basic_single_default:
                mSinglePosition = position;
                // MainActivity.mIsSingle = (position==1);
                break;

            case R.id.action_basic_religion_default:
                mReligionPosition = position;
                break;

            case R.id.action_basic_pet_default:
                mPetPosition = position;
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Save data to SharedPreference
    private void saveDataToSharedPreference() {
        MyInfoPreference.setStoredSingle(this, mSinglePosition);
    }


    // Save data to LeanCloud
    private void saveDataToLeanCloud() {
        AVObject mBasicInfo = new AVObject("BasicInfo");

        mBasicInfo.put("username", AVUser.getCurrentUser());

        mBasicInfo.put("educationLevel", mEducationPosition);
        mBasicInfo.put("occupation", mOccupationPosition);
        mBasicInfo.put("birthPlace", mBirthPlacePosition);
        mBasicInfo.put("workPlace", mWorkPlacePosition);
        mBasicInfo.put("oversea", mOverseaPosition);
        mBasicInfo.put("single", mSinglePosition);
        mBasicInfo.put("religion", mReligionPosition);
        mBasicInfo.put("pet", mPetPosition);

        mBasicInfo.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                        /*
                        if (e == null) {
                            // Successfully saved
                        } else {
                            // Fail to save
                        }
                        */
            }
        });
    }
}
