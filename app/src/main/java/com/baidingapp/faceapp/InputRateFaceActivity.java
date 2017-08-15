package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.baidingapp.faceapp.helper.ImageHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InputRateFaceActivity extends AppCompatActivity {

    private ScrollView mScrollView;
    private RadioGroup mRadioGroup;
    // private ArrayList<String> rateFacePhotoUrlList = new ArrayList<>();
    private ArrayList<AVObject> photoRatedList = new ArrayList<>();
    private int mImageUrlIndex = 0;

    private Button mNextButton;
    private Button mResultButton;
    private ImageView mFaceImageView;
    private BarChart mBarChart;
    private Spinner mSpinner;

    private int rateFaceScore;
    private int mSpinnerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rate_face);

        mScrollView = (ScrollView) findViewById(R.id.input_rate_scroll_view);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group_input_rate_face);
        mFaceImageView = (ImageView) findViewById(R.id.face_image_input_rate);


        // Get photoUrl in the RateFacePhoto table (class in LeanCloud)
        // Initialize the ImageView in getUrlOfRateFacePhotos()
        getUrlOfRateFacePhotos();


        // onSelect the Spinner
        mSpinner = (Spinner) findViewById(R.id.action_objective_question);
        ArrayAdapter mAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_occupations, android.R.layout.simple_spinner_item);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(mAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSpinnerPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // onCLick the RESULT button
        mResultButton = (Button) findViewById(R.id.action_show_result);
        mResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the results
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                showResult();
            }
        });


        // Plot the rates by others
        mBarChart = (BarChart) findViewById(R.id.input_rate_bar_chart);
        mBarChart.setNoDataText(getResources().getString(R.string.no_result_available));
        // mBarChart.setNoDataTextColor(R.color.red);


        // onCLick the NEXT button
        mNextButton = (Button) findViewById(R.id.action_show_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Note the different conditions for the two ifs
                if (mImageUrlIndex < photoRatedList.size()) {
                    saveDataToLeanCloud();
                }

                if ((mImageUrlIndex+1) < photoRatedList.size()) {
                    updateFaceImage();
                } else {
                    Toast.makeText(InputRateFaceActivity.this, R.string.no_face_photp_available, Toast.LENGTH_SHORT).show();
                    // InputRateFaceActivity.this.finish();
                }
            }
        });

    }



    // Get photoUrl in the RateFacePhoto table (class in LeanCloud)
    private void getUrlOfRateFacePhotos() {
        // String photoNotRated = "select * from RateFacePhoto where objectId != (select photoRated from RateFaceScore where userRating = AVUser.getCurrentUser().getObjectId()";
        
        // String photoNotRated = "select * from RateFacePhoto where objectId !=  '599115cb570c35006b684d5c' ";
        String currUsername = AVUser.getCurrentUser().getUsername();
        String photoNotRated = String.format("select * from RateFacePhoto where username != (select userRatedId from RateFaceScore where userRatingId = ? )");

        AVQuery.doCloudQueryInBackground(photoNotRated, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                for (AVObject photo : (avCloudQueryResult.getResults())) {
                    // rateFacePhotoUrlList.add(photo.getString("photoUrl"));
                    photoRatedList.add(photo);
                }

                // Initialize the ImageView
                if (photoRatedList.size() != 0) {
                    ImageHelper.ImageLoad(InputRateFaceActivity.this, photoRatedList.get(mImageUrlIndex).getString("photoUrl"), mFaceImageView);
                } else {
                    Toast.makeText(InputRateFaceActivity.this, R.string.no_face_photp_available, Toast.LENGTH_SHORT).show();
                    mNextButton.setEnabled(false);
                    mResultButton.setEnabled(false);
                }

            }
        }, currUsername);
    }

    /*
    private void getUrlOfRateFacePhotos() {
        // Inner Query
        AVObject currUser = AVObject.createWithoutData("_User", AVUser.getCurrentUser().getObjectId());
        AVQuery<AVObject> photoRatedQuery = new AVQuery<>("RateFaceScore");
        photoRatedQuery.whereEqualTo("userRating", currUser);

        // Main (Outer) Query
        AVQuery<AVObject> photoNotRatedQuery = new AVQuery<>("RateFacePhoto");
        photoNotRatedQuery.selectKeys(Arrays.asList("photoUrl", "userId", "username"));
        photoNotRatedQuery.whereNotEqualTo("username", AVUser.getCurrentUser().getUsername());

        photoNotRatedQuery.whereMatchesQuery("photoRated", photoRatedQuery);

        // Query result
        photoNotRatedQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject photo : list) {
                    rateFacePhotoUrlList.add(photo.getString("photoUrl"));
                    photoRatedList.add(photo);
                }

                // Initialize the ImageView
                ImageHelper.ImageLoad(InputRateFaceActivity.this, rateFacePhotoUrlList.get(mImageUrlIndex), mFaceImageView);
            }
        });
    }
    */

    /*
    private void getUrlOfRateFacePhotos() {
        // final ArrayList<String> photoUrlList = new ArrayList<>();
        AVQuery<AVObject> photoUrlQuery = new AVQuery<>("RateFacePhoto");
        photoUrlQuery.selectKeys(Arrays.asList("photoUrl", "userId", "username"));
        photoUrlQuery.whereNotEqualTo("username", AVUser.getCurrentUser().getUsername());

        photoUrlQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject photo : list) {
                    rateFacePhotoUrlList.add(photo.getString("photoUrl"));
                    photoRatedList.add(photo);
                }

                // Initialize the ImageView
                ImageHelper.ImageLoad(InputRateFaceActivity.this, rateFacePhotoUrlList.get(mImageUrlIndex), mFaceImageView);
            }
        });
        //return photoUrlList;
    }
    */


    private void updateFaceImage() {
        mRadioGroup.clearCheck();
        mBarChart.clear();
        mSpinner.setSelection(0);

        // Reset a new face image
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        mImageUrlIndex = mImageUrlIndex + 1;
        ImageHelper.ImageLoad(InputRateFaceActivity.this, photoRatedList.get(mImageUrlIndex).getString("photoUrl"), mFaceImageView);
    }



    // Get the rate score of face photo
    private void getRateFaceScore() {
        int selectedButtonId = mRadioGroup.getCheckedRadioButtonId();
        if (selectedButtonId != -1) {
            RadioButton radioButton = (RadioButton) findViewById(selectedButtonId);
            String radioString = radioButton.getText().toString();
            rateFaceScore = Integer.parseInt(radioString.substring(radioString.length() - 1));
        }
    }


    // Save data to the RateFaceScore table (class in LeanCloud)
    private void saveDataToLeanCloud() {
        getRateFaceScore();

        AVObject rateResult = new AVObject("RateFaceScore");

        rateResult.put("subQues", rateFaceScore);
        rateResult.put("objQues", mSpinnerPosition);
        rateResult.put("userRatingId", AVUser.getCurrentUser().getUsername());
        rateResult.put("userRatedId", photoRatedList.get(mImageUrlIndex).getString("username"));
        // photoRated's type is Pointer, which points to RateFacePhoto
        rateResult.put("userRating", AVUser.getCurrentUser());
        rateResult.put("photoRated", photoRatedList.get(mImageUrlIndex));

        rateResult.saveInBackground();
    }


    private void showResult() {

        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1f, 0.05f));
        barEntries.add(new BarEntry(2f, 0.1f));
        barEntries.add(new BarEntry(3f, 0.1f));
        barEntries.add(new BarEntry(4f, 0.33f));
        barEntries.add(new BarEntry(5f, 0.17f));
        barEntries.add(new BarEntry(6f, 0.05f));
        barEntries.add(new BarEntry(7f, 0.05f));
        barEntries.add(new BarEntry(8f, 0.05f));
        barEntries.add(new BarEntry(9f, 0.05f));
        barEntries.add(new BarEntry(10f, 0.05f));

        BarDataSet barDataSet = new BarDataSet(barEntries, "别人眼中的TA");
        BarData theData = new BarData(barDataSet);
        mBarChart.setDescription(null);
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }

}
