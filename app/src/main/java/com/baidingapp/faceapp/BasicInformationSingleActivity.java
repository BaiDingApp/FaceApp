package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.MyInfoPreference;

public class BasicInformationSingleActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private String basicInfoObjectId;

    private int mHeightValue;
    private int mWeightValue;
    private int mGenderPosition;
    private int mEducationPosition;
    private int mOccupationPosition;
    private int mBirthPlacePosition;
    private int mWorkPlacePosition;
    private int mOverseaPosition;
    private int mSinglePosition;
    private int mReligionPosition;
    private int mPetPosition;

    private EditText mHeightText;
    private EditText mWeightText;
    private Spinner mGenderSpinner;
    private Spinner mEducationSpinner;
    private Spinner mOccupationSpinner;
    private Spinner mBirthPlaceSpinner;
    private Spinner mWorkPlaceSpinner;
    private Spinner mOverseaSpinner;
    private Spinner mSingleSpinner;
    private Spinner mReligionSpinner;
    private Spinner mPetSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_single);


        // Height EditText
        mHeightText = (EditText) findViewById(R.id.action_height_single);

        // Weight EditText
        mWeightText = (EditText) findViewById(R.id.action_weight_single);


        // onClick Gender spinner
        mGenderSpinner = (Spinner) findViewById(R.id.action_basic_gender_single);
        ArrayAdapter mGenderAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_gender_levels, android.R.layout.simple_spinner_item);
        mGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(mGenderAdapter);
        mGenderSpinner.setOnItemSelectedListener(this);


        // onClick Education spinner
        mEducationSpinner = (Spinner) findViewById(R.id.action_basic_education_single);
        ArrayAdapter mEducationAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_education_levels, android.R.layout.simple_spinner_item);
        mEducationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEducationSpinner.setAdapter(mEducationAdapter);
        mEducationSpinner.setOnItemSelectedListener(this);


        // onClick Occupation spinner
        mOccupationSpinner = (Spinner) findViewById(R.id.action_basic_occupation_single);
        ArrayAdapter mOccupationAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_occupations, android.R.layout.simple_spinner_item);
        mOccupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOccupationSpinner.setAdapter(mOccupationAdapter);
        mOccupationSpinner.setOnItemSelectedListener(this);


        // onClick BirthPlace spinner
        mBirthPlaceSpinner = (Spinner) findViewById(R.id.action_basic_birth_place_single);
        ArrayAdapter mBirthPlaceAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_place, android.R.layout.simple_spinner_item);
        mBirthPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBirthPlaceSpinner.setAdapter(mBirthPlaceAdapter);
        mBirthPlaceSpinner.setOnItemSelectedListener(this);


        // onClick WorkPlace spinner
        mWorkPlaceSpinner = (Spinner) findViewById(R.id.action_basic_work_place_single);
        ArrayAdapter mWorkPlaceAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_place, android.R.layout.simple_spinner_item);
        mWorkPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWorkPlaceSpinner.setAdapter(mWorkPlaceAdapter);
        mWorkPlaceSpinner.setOnItemSelectedListener(this);


        // onClick Oversea spinner
        mOverseaSpinner = (Spinner) findViewById(R.id.action_basic_oversea_single);
        ArrayAdapter mOverseaAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mOverseaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOverseaSpinner.setAdapter(mOverseaAdapter);
        mOverseaSpinner.setOnItemSelectedListener(this);


        // onClick Single spinner
        mSingleSpinner = (Spinner) findViewById(R.id.action_basic_single_single);
        ArrayAdapter mSingleAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mSingleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSingleSpinner.setAdapter(mSingleAdapter);
        mSingleSpinner.setOnItemSelectedListener(this);


        // onClick Religion spinner
        mReligionSpinner = (Spinner) findViewById(R.id.action_basic_religion_single);
        ArrayAdapter mReligionAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mReligionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReligionSpinner.setAdapter(mReligionAdapter);
        mReligionSpinner.setOnItemSelectedListener(this);


        // onClick Pet spinner
        mPetSpinner = (Spinner) findViewById(R.id.action_basic_pet_single);
        ArrayAdapter mPetAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mPetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPetSpinner.setAdapter(mPetAdapter);
        mPetSpinner.setOnItemSelectedListener(this);


        // Create the profile by using info saved in SharedPreference when onCreate
        createProfileUsingSavedData();


        // onClick SUBMIT button
        Button mSubmitButton = (Button) findViewById(R.id.action_submit_basic_info_single);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the Height
                // If put it out, then Integer.parseInt() will produce 0
                // As it get the int value before inputting, so ...
                try {
                    mHeightValue = Integer.parseInt(mHeightText.getText().toString(), 10);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // Get the Weight
                try {
                    mWeightValue = Integer.parseInt(mWeightText.getText().toString(), 10);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                // Save data to SharedPreference
                saveDataToSharedPreference();

                // Save data to LeanCloud
                saveDataToLeanCloud();

                // If the former context is RegisterActivity, then finish the current context,
                //     and start the MainActivity.
                // Intent intent = new Intent(BasicInformationSingleActivity.this, MainActivity.class);
                // startActivity(intent);
                // finish();
            }
        });

    }


    // Identify the Spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.action_basic_gender_single:
                mGenderPosition = position;
                break;

            case R.id.action_basic_education_single:
                mEducationPosition = position;
                break;

            case R.id.action_basic_occupation_single:
                mOccupationPosition = position;
                break;

            case R.id.action_basic_birth_place_single:
                mBirthPlacePosition = position;
                break;

            case R.id.action_basic_work_place_single:
                mWorkPlacePosition = position;
                break;

            case R.id.action_basic_oversea_single:
                mOverseaPosition = position;
                break;

            case R.id.action_basic_single_single:
                mSinglePosition = position;
                break;

            case R.id.action_basic_religion_single:
                mReligionPosition = position;
                break;

            case R.id.action_basic_pet_single:
                mPetPosition = position;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    // Create the profile by using info saved in SharedPreference when onCreate
    private void createProfileUsingSavedData() {
        // Height
        mHeightValue = MyInfoPreference.getStoredHeight(BasicInformationSingleActivity.this);
        mHeightText.setText(String.valueOf(mHeightValue));

        // Weight
        mWeightValue = MyInfoPreference.getStoredWeight(BasicInformationSingleActivity.this);
        mWeightText.setText(String.valueOf(mWeightValue));

        // Gender
        mGenderPosition = MyInfoPreference.getStoredGender(BasicInformationSingleActivity.this);
        mGenderSpinner.setSelection(mGenderPosition);

        // Education Level
        mEducationPosition = MyInfoPreference.getStoredEducation(BasicInformationSingleActivity.this);
        mEducationSpinner.setSelection(mEducationPosition);

        // Occupation
        mOccupationPosition = MyInfoPreference.getStoredOccupation(BasicInformationSingleActivity.this);
        mOccupationSpinner.setSelection(mOccupationPosition);

        // Birth Place
        mBirthPlacePosition = MyInfoPreference.getStoredBirthPlace(BasicInformationSingleActivity.this);
        mBirthPlaceSpinner.setSelection(mBirthPlacePosition);

        //Work Place
        mWorkPlacePosition = MyInfoPreference.getStoredWorkPlace(BasicInformationSingleActivity.this);
        mWorkPlaceSpinner.setSelection(mWorkPlacePosition);

        // Oversea
        mOverseaPosition = MyInfoPreference.getStoredOversea(BasicInformationSingleActivity.this);
        mOverseaSpinner.setSelection(mOverseaPosition);

        // Single
        mSinglePosition = MyInfoPreference.getStoredSingle(BasicInformationSingleActivity.this);
        mSingleSpinner.setSelection(mSinglePosition);

        // Religion
        mReligionPosition = MyInfoPreference.getStoredReligion(BasicInformationSingleActivity.this);
        mReligionSpinner.setSelection(mReligionPosition);

        // Pet
        mPetPosition = MyInfoPreference.getStoredPet(BasicInformationSingleActivity.this);
        mPetSpinner.setSelection(mPetPosition);
    }


    // Save data to SharedPreference
    private void saveDataToSharedPreference() {
        MyInfoPreference.setStoredHeight(BasicInformationSingleActivity.this, mHeightValue);
        MyInfoPreference.setStoredWeight(BasicInformationSingleActivity.this, mWeightValue);

        MyInfoPreference.setStoredGender(BasicInformationSingleActivity.this, mGenderPosition);
        MyInfoPreference.setStoredEducation(BasicInformationSingleActivity.this, mEducationPosition);
        MyInfoPreference.setStoredOccupation(BasicInformationSingleActivity.this, mOccupationPosition);
        MyInfoPreference.setStoredBirthPlace(BasicInformationSingleActivity.this, mBirthPlacePosition);
        MyInfoPreference.setStoredWorkPlace(BasicInformationSingleActivity.this, mWorkPlacePosition);
        MyInfoPreference.setStoredOversea(BasicInformationSingleActivity.this, mOverseaPosition);
        MyInfoPreference.setStoredSingle(BasicInformationSingleActivity.this, mSinglePosition);
        MyInfoPreference.setStoredReligion(BasicInformationSingleActivity.this, mReligionPosition);
        MyInfoPreference.setStoredPet(BasicInformationSingleActivity.this, mPetPosition);
    }


    // Save data to LeanCloud
    private void saveDataToLeanCloud() {
        basicInfoObjectId = MyInfoPreference.getStoredBasicInfoObjectId(BasicInformationSingleActivity.this);

        if (basicInfoObjectId != null) {
            final AVObject mBasicInfo = AVObject.createWithoutData("BasicInfo", basicInfoObjectId);

            mBasicInfo.put("height", mHeightValue);
            mBasicInfo.put("weight", mWeightValue);
            mBasicInfo.put("gender", mGenderPosition);
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
                    if (e == null) {
                        Toast.makeText(BasicInformationSingleActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BasicInformationSingleActivity.this, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            final AVObject mBasicInfo = new AVObject("BasicInfo");

            // Create the pointer that points to _User
            mBasicInfo.put("username", AVUser.getCurrentUser().getUsername());
            mBasicInfo.put("userId", AVUser.getCurrentUser());

            mBasicInfo.put("height", mHeightValue);
            mBasicInfo.put("weight", mWeightValue);
            mBasicInfo.put("gender", mGenderPosition);
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
                    if (e == null) {
                        // Save the objectId of BasicInfo to SharedPreference
                        basicInfoObjectId = mBasicInfo.getObjectId();
                        MyInfoPreference.setStoredBasicInfoObjectId(BasicInformationSingleActivity.this, basicInfoObjectId);

                        Toast.makeText(BasicInformationSingleActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(BasicInformationSingleActivity.this, R.string.save_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }  // End of "Save data to LeanCloud"

}






// ---------------------------------------- Backup Codes --------------------------------------- //

/*
        // onSelect SINGLE spinner
        mSingleSpinner = (Spinner) findViewById(R.id.action_basic_single_single);
        ArrayAdapter mSingleAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mSingleSpinner.setAdapter(mSingleAdapter);
        mSingleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // MainActivity.mIsSingle = (position==1);
                mSinglePosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Create the profile by using info saved in SharedPreference when onCreate
        createProfileUsingSavedData();


        // onClick SUBMIT button
        Button mSubmitButton = (Button) findViewById(R.id.action_submit_basic_info_single);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Intent intent = new Intent(BasicInformationSingleActivity.this, MainActivity.class);
                // startActivity(intent);

                saveDataToSharedPreference();
            }
        });
*/


/*
    // Create the profile by using info saved in SharedPreference when onCreate
    private void createProfileUsingSavedData() {
        mSinglePosition = MyInfoPreference.getStoredSingle(BasicInformationSingleActivity.this);
        mSingleSpinner.setSelection(mSinglePosition);
    }


    // Save data to SharedPreference
    private void saveDataToSharedPreference() {
        MyInfoPreference.setStoredSingle(this, mSinglePosition);
    }
*/