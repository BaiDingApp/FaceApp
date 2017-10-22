package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
// import android.widget.AdapterView;
// import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
// import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InputRateFaceActivity extends AppCompatActivity {

    private ScrollView mScrollView;
    private ImageView mFaceImageView;
    private Button mNextButton;
    private ArrayList<AVObject> photoRatedList = new ArrayList<>();
    private int mPhotoIndex = 0;

    private RadioGroup mFirstRadioGroup;
    private RadioGroup mSecondRadioGroup;
    private RadioGroup mThirdRadioGroup;
    private RadioGroup mFourthRadioGroup;
    private RadioGroup mFifthRadioGroup;

    private Button mFirstResultButton;
    private Button mSecondResultButton;
    private Button mThirdResultButton;
    private Button mFourthResultButton;
    private Button mFifthResultButton;

    private BarChart mFirstBarChart;
    private BarChart mSecondBarChart;
    private BarChart mThirdBarChart;
    private BarChart mFourthBarChart;
    private BarChart mFifthBarChart;

//    private Spinner mSpinner;

    private TextView mFirstLeftField;
    private TextView mFirstRightField;
    private TextView mSecondLeftField;
    private TextView mSecondRightField;
    private TextView mThirdLeftField;
    private TextView mThirdRightField;
    private TextView mFourthLeftField;
    private TextView mFourthRightField;
    private TextView mFifthLeftField;
    private TextView mFifthRightField;

    private String mFirstTrait;
    private String mSecondTrait;
    private String mThirdTrait;
    private String mFourthTrait;
    private String mFifthTrait;

    private int mFirstRateFaceScore;
    private int mSecondRateFaceScore;
    private int mThirdRateFaceScore;
    private int mFourthRateFaceScore;
    private int mFifthRateFaceScore;

    private String mGroupId;

//    private int mSpinnerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rate_face);

        mFaceImageView = (ImageView) findViewById(R.id.face_image_input_rate);
        mScrollView = (ScrollView) findViewById(R.id.input_rate_scroll_view);

        // Get photoUrl in the RateFacePhoto table (class in LeanCloud)
        // Initialize the ImageView in getUrlOfRateFacePhotos()
        getUrlOfRateFacePhotos();


        // Initialize the Questions
        // The First Question
        mFirstLeftField = (TextView) findViewById(R.id.first_input_rate).findViewById(R.id.left_field);
        mFirstRightField = (TextView) findViewById(R.id.first_input_rate).findViewById(R.id.right_field);
        // The Second Question
        mSecondLeftField = (TextView) findViewById(R.id.second_input_rate).findViewById(R.id.left_field);
        mSecondRightField = (TextView) findViewById(R.id.second_input_rate).findViewById(R.id.right_field);
        // The Third Question
        mThirdLeftField = (TextView) findViewById(R.id.third_input_rate).findViewById(R.id.left_field);
        mThirdRightField = (TextView) findViewById(R.id.third_input_rate).findViewById(R.id.right_field);
        // The Fourth Question
        mFourthLeftField = (TextView) findViewById(R.id.fourth_input_rate).findViewById(R.id.left_field);
        mFourthRightField = (TextView) findViewById(R.id.fourth_input_rate).findViewById(R.id.right_field);
        // The Fifth Question
        mFifthLeftField = (TextView) findViewById(R.id.fifth_input_rate).findViewById(R.id.left_field);
        mFifthRightField = (TextView) findViewById(R.id.fifth_input_rate).findViewById(R.id.right_field);


        // Determine the social trait group to be used TODAY
        String[] mGroupIdArray = getResources().getStringArray(R.array.group_id_traits);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date mCurrentDate = new Date(System.currentTimeMillis());  //获取当前时间
        try {
            final Date mAppLaunchDate = dateFormat.parse("2017-10-22 00:00:00");
            long timeDiff = mCurrentDate.getTime() - mAppLaunchDate.getTime();
            int dayDiff  = (int) timeDiff / (1000 * 3600 * 24);

            mGroupId = mGroupIdArray[dayDiff];
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(InputRateFaceActivity.this, "无法设定初始时间", Toast.LENGTH_SHORT).show();
        }
        

        // Set the Questions
        setTextForQuestion();


        // RadioGroups
        mFirstRadioGroup = (RadioGroup) findViewById(R.id.first_input_rate).findViewById(R.id.rate_radio_group);
        mSecondRadioGroup = (RadioGroup) findViewById(R.id.second_input_rate).findViewById(R.id.rate_radio_group);
        mThirdRadioGroup = (RadioGroup) findViewById(R.id.third_input_rate).findViewById(R.id.rate_radio_group);
        mFourthRadioGroup = (RadioGroup) findViewById(R.id.fourth_input_rate).findViewById(R.id.rate_radio_group);
        mFifthRadioGroup = (RadioGroup) findViewById(R.id.fifth_input_rate).findViewById(R.id.rate_radio_group);


        // Plot the BarChart of the rates by others
        // The first BarChart
        // mBarChart.setNoDataTextColor(R.color.red);
        mFirstBarChart = (BarChart) findViewById(R.id.first_input_rate).findViewById(R.id.plot_bar_chart);
        mFirstBarChart.setNoDataText(getResources().getString(R.string.no_result_available));
        // The Second BarChart
        mSecondBarChart = (BarChart) findViewById(R.id.second_input_rate).findViewById(R.id.plot_bar_chart);
        mSecondBarChart.setNoDataText(getResources().getString(R.string.no_result_available));
        // The Third BarChart
        mThirdBarChart = (BarChart) findViewById(R.id.third_input_rate).findViewById(R.id.plot_bar_chart);
        mThirdBarChart.setNoDataText(getResources().getString(R.string.no_result_available));
        // The Fourth BarChart
        mFourthBarChart = (BarChart) findViewById(R.id.fourth_input_rate).findViewById(R.id.plot_bar_chart);
        mFourthBarChart.setNoDataText(getResources().getString(R.string.no_result_available));
        // The Fifth BarChart
        mFifthBarChart = (BarChart) findViewById(R.id.fifth_input_rate).findViewById(R.id.plot_bar_chart);
        mFifthBarChart.setNoDataText(getResources().getString(R.string.no_result_available));

/*
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
*/

        // onCLick the RESULT button
        // The First Question
        mFirstResultButton = (Button) findViewById(R.id.first_input_rate).findViewById(R.id.action_show_result);
        mFirstResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFirstOthersRateScoresAndShowResult(photoRatedList.get(mPhotoIndex).getObjectId());
            }
        });
        // The Second Question
        mSecondResultButton = (Button) findViewById(R.id.second_input_rate).findViewById(R.id.action_show_result);
        mSecondResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSecondOthersRateScoresAndShowResult(photoRatedList.get(mPhotoIndex).getObjectId());
            }
        });
        // The Third Question
        mThirdResultButton = (Button) findViewById(R.id.third_input_rate).findViewById(R.id.action_show_result);
        mThirdResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getThirdOthersRateScoresAndShowResult(photoRatedList.get(mPhotoIndex).getObjectId());
            }
        });
        // The Fourth Question
        mFourthResultButton = (Button) findViewById(R.id.fourth_input_rate).findViewById(R.id.action_show_result);
        mFourthResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFourthOthersRateScoresAndShowResult(photoRatedList.get(mPhotoIndex).getObjectId());
            }
        });
        // The Fifth Question
        mFifthResultButton = (Button) findViewById(R.id.fifth_input_rate).findViewById(R.id.action_show_result);
        mFifthResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFifthOthersRateScoresAndShowResult(photoRatedList.get(mPhotoIndex).getObjectId());
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });


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
                    mFirstResultButton.setEnabled(false);
                    mSecondResultButton.setEnabled(false);
                    mThirdResultButton.setEnabled(false);
                    mFourthResultButton.setEnabled(false);
                    mFifthResultButton.setEnabled(false);
                    // InputRateFaceActivity.this.finish();
                }
            }
        });

    }


    // Get the questions from LeanCLoud
    // Set the question, or left and right fields to the TextView
    private void setTextForQuestion() {
        AVQuery<AVObject> questions = new AVQuery<>("LikertQuestions");
        questions.whereEqualTo("groupId", mGroupId);

        questions.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                mFirstLeftField.setText(list.get(0).getString("leftField"));
                mFirstRightField.setText(list.get(0).getString("rightField"));
                mFirstTrait = list.get(0).getString("questionEn");

                mSecondLeftField.setText(list.get(1).getString("leftField"));
                mSecondRightField.setText(list.get(1).getString("rightField"));
                mSecondTrait = list.get(1).getString("questionEn");

                mThirdLeftField.setText(list.get(2).getString("leftField"));
                mThirdRightField.setText(list.get(2).getString("rightField"));
                mThirdTrait = list.get(2).getString("questionEn");

                mFourthLeftField.setText(list.get(3).getString("leftField"));
                mFourthRightField.setText(list.get(3).getString("rightField"));
                mFourthTrait = list.get(3).getString("questionEn");

                mFifthLeftField.setText(list.get(4).getString("leftField"));
                mFifthRightField.setText(list.get(4).getString("rightField"));
                mFifthTrait = list.get(4).getString("questionEn");
            }
        });
    }


    // Get photoUrl in the RateFacePhoto table (class in LeanCloud)
    // Use CQL query method
    // 1. Users cannot see the photos they saw before; 2. They can see their own photos once
    private void getUrlOfRateFacePhotos() {
        // String photoNotRated = "select * from RateFacePhoto where objectId !=  '599115cb570c35006b684d5c' ";
        String currUsername = AVUser.getCurrentUser().getUsername();
        String photoNotRated = "select * from RateFacePhoto where username != (select usernameRated from RateFaceScore where usernameRating = ? limit 1000) limit 1000 ";

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
                    mFirstResultButton.setEnabled(false);
                    mSecondResultButton.setEnabled(false);
                    mThirdResultButton.setEnabled(false);
                    mFourthResultButton.setEnabled(false);
                    mFifthResultButton.setEnabled(false);
                }
            }
        }, currUsername);
    }


    private void updateFaceImage() {
        mFirstRadioGroup.clearCheck();
        mSecondRadioGroup.clearCheck();
        mThirdRadioGroup.clearCheck();
        mFourthRadioGroup.clearCheck();
        mFifthRadioGroup.clearCheck();

        mFirstBarChart.clear();
        mSecondBarChart.clear();
        mThirdBarChart.clear();
        mFourthBarChart.clear();
        mFifthBarChart.clear();
        // mSpinner.setSelection(0);

        // Reset a new face image
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        mPhotoIndex = mPhotoIndex + 1;
        ImageHelper.ImageLoad(InputRateFaceActivity.this, photoRatedList.get(mPhotoIndex).getString("photoUrl"), mFaceImageView);
    }


    // Get All rate scores of face photo
    private void getAllRateFaceScores() {
        mFirstRateFaceScore = getRateFaceScore(mFirstRadioGroup);
        mSecondRateFaceScore = getRateFaceScore(mSecondRadioGroup);
        mThirdRateFaceScore = getRateFaceScore(mThirdRadioGroup);
        mFourthRateFaceScore = getRateFaceScore(mFourthRadioGroup);
        mFifthRateFaceScore = getRateFaceScore(mFifthRadioGroup);
    }
    // Get A rate score of the face photo
    private int getRateFaceScore(RadioGroup mRadioGroup) {
        int selectedRadioGroup = mRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedRadioGroup);
        String radioString = radioButton.getText().toString();

        int rateFaceScore = 0;

        if (selectedRadioGroup != -1) {
            if (selectedRadioGroup == R.id.rate_value_10) {
                rateFaceScore = Integer.parseInt(radioString.substring(radioString.length() - 2));
            } else {
                rateFaceScore = Integer.parseInt(radioString.substring(radioString.length() - 1));
            }
        }

        return rateFaceScore;
    }


    // Save data to the RateFaceScore table (class in LeanCloud)
    private void saveDataToLeanCloud() {
        getAllRateFaceScores();

        AVObject rateResult = new AVObject("RateFaceScore");

        rateResult.put(mFirstTrait,  mFirstRateFaceScore);
        rateResult.put(mSecondTrait, mSecondRateFaceScore);
        rateResult.put(mThirdTrait,  mThirdRateFaceScore);
        rateResult.put(mFourthTrait, mFourthRateFaceScore);
        rateResult.put(mFifthTrait,  mFifthRateFaceScore);
        // rateResult.put("objQues", mSpinnerPosition);
        rateResult.put("usernameRating", AVUser.getCurrentUser().getUsername());
        rateResult.put("usernameRated", photoRatedList.get(mPhotoIndex).getString("username"));
        // photoRated's type is Pointer, which points to RateFacePhoto
        rateResult.put("userIdRating", AVUser.getCurrentUser());
        rateResult.put("photoIdRated", photoRatedList.get(mPhotoIndex));

        rateResult.saveInBackground();
    }


    // Get the rate scores by others from LeanCloud
    // Show the results via BarChart
    // Plot the First BarChart
    private void getFirstOthersRateScoresAndShowResult(String rateFacePhotoId) {
        AVObject photoRatedObject = AVObject.createWithoutData("RateFacePhoto", rateFacePhotoId);

        AVQuery<AVObject> rateScoreQuery = new AVQuery<>("RateFaceScore");
        rateScoreQuery.selectKeys(Arrays.asList(mFirstTrait, "photoIdRated"));
        rateScoreQuery.whereEqualTo("photoIdRated", photoRatedObject);
        rateScoreQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size()>0) {
                    int[] allRateScores = new int[list.size()];

                    int i = 0;
                    for (AVObject object : list) {
                        allRateScores[i] = object.getInt(mFirstTrait);
                        i++;
                    }

                    plotBarChart(allRateScores, mFirstBarChart);
                } else {
                    Toast.makeText(InputRateFaceActivity.this,
                            R.string.no_result_due_to_limited_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Plot the Second BarChart
    private void getSecondOthersRateScoresAndShowResult(String rateFacePhotoId) {
        AVObject photoRatedObject = AVObject.createWithoutData("RateFacePhoto", rateFacePhotoId);

        AVQuery<AVObject> rateScoreQuery = new AVQuery<>("RateFaceScore");
        rateScoreQuery.selectKeys(Arrays.asList(mSecondTrait, "photoIdRated"));
        rateScoreQuery.whereEqualTo("photoIdRated", photoRatedObject);
        rateScoreQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size()>0) {
                    int[] allRateScores = new int[list.size()];

                    int i = 0;
                    for (AVObject object : list) {
                        allRateScores[i] = object.getInt(mSecondTrait);
                        i++;
                    }

                    plotBarChart(allRateScores, mSecondBarChart);
                } else {
                    Toast.makeText(InputRateFaceActivity.this,
                            R.string.no_result_due_to_limited_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Plot the Third BarChart
    private void getThirdOthersRateScoresAndShowResult(String rateFacePhotoId) {
        AVObject photoRatedObject = AVObject.createWithoutData("RateFacePhoto", rateFacePhotoId);

        AVQuery<AVObject> rateScoreQuery = new AVQuery<>("RateFaceScore");
        rateScoreQuery.selectKeys(Arrays.asList(mThirdTrait, "photoIdRated"));
        rateScoreQuery.whereEqualTo("photoIdRated", photoRatedObject);
        rateScoreQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size()>0) {
                    int[] allRateScores = new int[list.size()];

                    int i = 0;
                    for (AVObject object : list) {
                        allRateScores[i] = object.getInt(mThirdTrait);
                        i++;
                    }

                    plotBarChart(allRateScores, mThirdBarChart);
                } else {
                    Toast.makeText(InputRateFaceActivity.this,
                            R.string.no_result_due_to_limited_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Plot the Fourth BarChart
    private void getFourthOthersRateScoresAndShowResult(String rateFacePhotoId) {
        AVObject photoRatedObject = AVObject.createWithoutData("RateFacePhoto", rateFacePhotoId);

        AVQuery<AVObject> rateScoreQuery = new AVQuery<>("RateFaceScore");
        rateScoreQuery.selectKeys(Arrays.asList(mFourthTrait, "photoIdRated"));
        rateScoreQuery.whereEqualTo("photoIdRated", photoRatedObject);
        rateScoreQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size()>0) {
                    int[] allRateScores = new int[list.size()];

                    int i = 0;
                    for (AVObject object : list) {
                        allRateScores[i] = object.getInt(mFourthTrait);
                        i++;
                    }

                    plotBarChart(allRateScores, mFourthBarChart);
                } else {
                    Toast.makeText(InputRateFaceActivity.this,
                            R.string.no_result_due_to_limited_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Plot the Fifth BarChart
    private void getFifthOthersRateScoresAndShowResult(String rateFacePhotoId) {
        AVObject photoRatedObject = AVObject.createWithoutData("RateFacePhoto", rateFacePhotoId);

        AVQuery<AVObject> rateScoreQuery = new AVQuery<>("RateFaceScore");
        rateScoreQuery.selectKeys(Arrays.asList(mFifthTrait, "photoIdRated"));
        rateScoreQuery.whereEqualTo("photoIdRated", photoRatedObject);
        rateScoreQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size()>0) {
                    int[] allRateScores = new int[list.size()];

                    int i = 0;
                    for (AVObject object : list) {
                        allRateScores[i] = object.getInt(mFifthTrait);
                        i++;
                    }

                    plotBarChart(allRateScores, mFifthBarChart);
                } else {
                    Toast.makeText(InputRateFaceActivity.this,
                            R.string.no_result_due_to_limited_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void plotBarChart(int[] allRateScores, BarChart mBarChart) {
        List<BarEntry> barEntries = StatHelper.getBarEntry(allRateScores);
        BarDataSet barDataSet = new BarDataSet(barEntries, "别人眼中的TA");
        BarData theData = new BarData(barDataSet);
        mBarChart.setDescription(null);
        // mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }
}






// ---------------------------------- Backup Code ------------------------------------ //

/*
    private void getOthersRateScoresAndShowResult(String rateFacePhotoId) {
        AVObject photoRatedObject = AVObject.createWithoutData("RateFacePhoto", rateFacePhotoId);

        AVQuery<AVObject> rateScoreQuery = new AVQuery<>("RateFaceScore");
        rateScoreQuery.selectKeys(Arrays.asList(mFirstTrait, "photoIdRated"));
        rateScoreQuery.whereEqualTo("photoIdRated", photoRatedObject);
        rateScoreQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list.size()>0) {
                    int[] allRateScores = new int[list.size()];

                    int i = 0;
                    for (AVObject object : list) {
                        allRateScores[i] = object.getInt(mFirstTrait);
                        i++;
                    }

                    plotBarChart(allRateScores, mFirstBarChart);
                } else {
                    Toast.makeText(InputRateFaceActivity.this,
                            R.string.no_result_due_to_limited_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
*/


/*
    private void plotBarChart(int[] allFirstRateScores) {
        List<BarEntry> barEntries = StatHelper.getBarEntry(allRateScores);
        BarDataSet barDataSet = new BarDataSet(barEntries, "别人眼中的TA");
        BarData theData = new BarData(barDataSet);
        mFirstBarChart.setDescription(null);
        // mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mFirstBarChart.setData(theData);
        mFirstBarChart.animateY(1000);
        mFirstBarChart.invalidate();
    }
*/


/*
    private void getAllRateFaceScores() {
        int selectedFirstRadioGroup = mFirstRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(selectedFirstRadioGroup);
        String radioString = radioButton.getText().toString();

        if (selectedFirstRadioGroup != -1) {
            if (selectedFirstRadioGroup == R.id.rate_value_10) {
                rateFaceScore = Integer.parseInt(radioString.substring(radioString.length() - 2));
            } else {
                rateFaceScore = Integer.parseInt(radioString.substring(radioString.length() - 1));
            }
        }
    }
*/


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