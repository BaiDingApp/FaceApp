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
import com.baidingapp.faceapp.helper.StatHelper;
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
    private ArrayList<AVObject> photoRatedList = new ArrayList<>();
    private int mPhotoIndex = 0;

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
                getOthersRateScoresAndShowResult(photoRatedList.get(mPhotoIndex).getObjectId());
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
                saveDataToLeanCloud();

                if ((mPhotoIndex+1) < photoRatedList.size()) {
                    updateFaceImage();
                } else {
                    Toast.makeText(InputRateFaceActivity.this, R.string.no_face_photo_available, Toast.LENGTH_SHORT).show();
                    mNextButton.setEnabled(false);
                    mResultButton.setEnabled(false);
                    // InputRateFaceActivity.this.finish();
                }
            }
        });

    }


    // Get photoUrl in the RateFacePhoto table (class in LeanCloud)
    // Use CQL query method
    // 1. Users cannot see the photos they saw before; 2. They can see their own photos once
    private void getUrlOfRateFacePhotos() {
        // String photoNotRated = "select * from RateFacePhoto where objectId !=  '599115cb570c35006b684d5c' ";
        String currUsername = AVUser.getCurrentUser().getUsername();
        String photoNotRated = "select * from RateFacePhoto where username != (select usernameRated from RateFaceScore where usernameRating = ? )" ;

        AVQuery.doCloudQueryInBackground(photoNotRated, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                for (AVObject photo : (avCloudQueryResult.getResults())) {
                    photoRatedList.add(photo);
                }

                // Initialize the ImageView
                if (photoRatedList.size() != 0) {
                    ImageHelper.ImageLoad(InputRateFaceActivity.this, photoRatedList.get(mPhotoIndex).getString("photoUrl"), mFaceImageView);
                } else {
                    ImageHelper.ImageLoad(InputRateFaceActivity.this, null, mFaceImageView);
                    Toast.makeText(InputRateFaceActivity.this, R.string.no_face_photo_available, Toast.LENGTH_SHORT).show();
                    mNextButton.setEnabled(false);
                    mResultButton.setEnabled(false);
                }
            }
        }, currUsername);
    }


    private void updateFaceImage() {
        mRadioGroup.clearCheck();
        mBarChart.clear();
        mSpinner.setSelection(0);

        // Reset a new face image
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        mPhotoIndex = mPhotoIndex + 1;
        ImageHelper.ImageLoad(InputRateFaceActivity.this, photoRatedList.get(mPhotoIndex).getString("photoUrl"), mFaceImageView);
    }


    // Get the rate score of face photo
    private void getRateFaceScore() {
        int selectedButtonId = mRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedButtonId);
        String radioString = radioButton.getText().toString();

        if (selectedButtonId != -1) {
            if (selectedButtonId == R.id.rate_value_10) {
                rateFaceScore = Integer.parseInt(radioString.substring(radioString.length() - 2));
            } else {
                rateFaceScore = Integer.parseInt(radioString.substring(radioString.length() - 1));
            }
        }
    }


    // Save data to the RateFaceScore table (class in LeanCloud)
    private void saveDataToLeanCloud() {
        getRateFaceScore();

        AVObject rateResult = new AVObject("RateFaceScore");

        rateResult.put("subQues", rateFaceScore);
        rateResult.put("objQues", mSpinnerPosition);
        rateResult.put("usernameRating", AVUser.getCurrentUser().getUsername());
        rateResult.put("usernameRated", photoRatedList.get(mPhotoIndex).getString("username"));
        // photoRated's type is Pointer, which points to RateFacePhoto
        rateResult.put("userIdRating", AVUser.getCurrentUser());
        rateResult.put("photoIdRated", photoRatedList.get(mPhotoIndex));

        rateResult.saveInBackground();
    }


    // Get the rate scores by others from LeanCloud
    // Show the results via BarChart
    private void getOthersRateScoresAndShowResult(String rateFacePhotoId) {
        AVObject photoRatedObject = AVObject.createWithoutData("RateFacePhoto", rateFacePhotoId);

        AVQuery<AVObject> rateScoreQuery = new AVQuery<>("RateFaceScore");
        rateScoreQuery.selectKeys(Arrays.asList("subQues", "photoIdRated"));
        rateScoreQuery.whereEqualTo("photoIdRated", photoRatedObject);
        rateScoreQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size()>0) {
                    int[] allRateScores = new int[list.size()];

                    int i = 0;
                    for (AVObject object : list) {
                        allRateScores[i] = object.getInt("subQues");
                        i++;
                    }

                    plotBarChart(allRateScores);
                } else {
                    Toast.makeText(InputRateFaceActivity.this,
                            R.string.no_result_due_to_limited_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void plotBarChart(int[] allRateScores) {
        List<BarEntry> barEntries = StatHelper.getBarEntry(allRateScores);
        BarDataSet barDataSet = new BarDataSet(barEntries, "别人眼中的TA");
        BarData theData = new BarData(barDataSet);
        mBarChart.setDescription(null);
        // mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }

/*
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
*/
}






// Backup Code
// Get photoUrl in the RateFacePhoto table (class in LeanCloud)
    /*
    private void getUrlOfRateFacePhotos() {
        // Inner Query
        String currUsername = AVUser.getCurrentUser().getUsername();
        AVQuery<AVObject> photoRatedQuery = new AVQuery<>("RateFaceScore");
        photoRatedQuery.whereEqualTo("userRatingId", currUsername);

        // Main (Outer) Query
        AVQuery<AVObject> photoNotRatedQuery = new AVQuery<>("RateFacePhoto");
        photoNotRatedQuery.selectKeys(Arrays.asList("photoUrl", "userId", "username"));
        photoNotRatedQuery.whereNotEqualTo("username", currUsername);

        photoNotRatedQuery.whereDoesNotMatchQuery("photoRated", photoRatedQuery);

        // Query result
        photoNotRatedQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject photo : list) {
                    photoRatedList.add(photo);
                }

                // Initialize the ImageView
                if (photoRatedList.size() != 0) {
                    ImageHelper.ImageLoad(InputRateFaceActivity.this, photoRatedList.get(mPhotoIndex).getString("photoUrl"), mFaceImageView);
                } else {
                    Toast.makeText(InputRateFaceActivity.this, R.string.no_face_photo_available, Toast.LENGTH_SHORT).show();
                    mNextButton.setEnabled(false);
                    mResultButton.setEnabled(false);
                }
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
                    photoRatedList.add(photo);
                }

                // Initialize the ImageView
                if (photoRatedList.size() != 0) {
                    ImageHelper.ImageLoad(InputRateFaceActivity.this, photoRatedList.get(mPhotoIndex).getString("photoUrl"), mFaceImageView);
                } else {
                    Toast.makeText(InputRateFaceActivity.this, R.string.no_face_photo_available, Toast.LENGTH_SHORT).show();
                    mNextButton.setEnabled(false);
                    mResultButton.setEnabled(false);
                }
            }
        });
    }
    */