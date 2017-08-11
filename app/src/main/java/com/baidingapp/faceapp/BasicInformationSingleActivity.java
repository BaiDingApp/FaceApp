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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_single);


        // onSelect SINGLE spinner
        Spinner mSingleSpinner = (Spinner) findViewById(R.id.action_basic_single_single);
        ArrayAdapter mSingleAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mSingleSpinner.setAdapter(mSingleAdapter);
        mSingleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // MainActivity.mIsSingle = (position==1);
                mSinglePosition = position;
                saveDataToSharedPreference();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // onClick SUBMIT button
        Button mSubmitButton = (Button) findViewById(R.id.action_submit_basic_info_single);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Intent intent = new Intent(BasicInformationSingleActivity.this, MainActivity.class);
                startActivity(intent);
                */
            }
        });
    }


    // Save data to SharedPreference
    private void saveDataToSharedPreference() {
        MyInfoPreference.setStoredSingle(this, mSinglePosition);
    }
}
