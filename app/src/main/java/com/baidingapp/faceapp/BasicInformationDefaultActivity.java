package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class BasicInformationDefaultActivity extends AppCompatActivity {

    private boolean mIsSingle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information_default);

        Spinner mSingleSpinner = (Spinner) findViewById(R.id.action_basic_single_default);
        ArrayAdapter mSingleAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mSingleSpinner.setAdapter(mSingleAdapter);
        mSingleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==1) {
                    mIsSingle = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button mSubmitButton = (Button) findViewById(R.id.action_submit_basic_info_default);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                // If the former context is RegisterActivity, then finish the current context,
                //     and start the MainActivity.
                Intent intent = MainActivity.newIntent(BasicInformationDefaultActivity.this, mIsSingle);
                startActivity(intent);
                finish();
            }
        });

    }

}
