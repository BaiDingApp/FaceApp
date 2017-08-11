package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.baidingapp.faceapp.helper.MyInfoPreference;

public class BasicInformationSingleActivity extends AppCompatActivity {

    private int mSinglePosition;

    private Spinner mSingleSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_single);


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
                /*
                Intent intent = new Intent(BasicInformationSingleActivity.this, MainActivity.class);
                startActivity(intent);
                */
                saveDataToSharedPreference();
            }
        });
    }


    // Create the profile by using info saved in SharedPreference when onCreate
    private void createProfileUsingSavedData() {
        mSinglePosition = MyInfoPreference.getStoredSingle(BasicInformationSingleActivity.this);
        mSingleSpinner.setSelection(mSinglePosition);
    }


    // Save data to SharedPreference
    private void saveDataToSharedPreference() {
        MyInfoPreference.setStoredSingle(this, mSinglePosition);
    }
}
