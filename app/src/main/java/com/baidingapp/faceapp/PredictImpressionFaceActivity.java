package com.baidingapp.faceapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.ImageHelper;
import com.baidingapp.faceapp.helper.MyInfoPreference;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PredictImpressionFaceActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_CODE_IMAGE = 2;

    private ImageView mFaceImageView;
    private Button mUploadButton;
    private Button mPredictImpressionButton;
    private String mImagePath;
    private AVFile mImageFile = null;
    private String predictFacePhotoId;
    private BarChart mBarChart;

    /*
    private Spinner mGenderSpinner;
    private Spinner mAgeSpinner;
    private Spinner mEducationSpinner;
    private Spinner mOccupationSpinner;
    private Spinner mBirthPlaceSpinner;
    private Spinner mWorkPlaceSpinner;
    private Spinner mOverseaSpinner;
    private Spinner mSingleSpinner;
    private Spinner mReligionSpinner;
    private Spinner mPetSpinner;
*/
    private int mGenderPosition;
    private int mAgePosition;
    private int mEducationPosition;
    private int mOccupationPosition;
    private int mBirthPlacePosition;
    private int mWorkPlacePosition;
    private int mOverseaPosition;
    private int mSinglePosition;
    private int mReligionPosition;
    private int mPetPosition;

    private int mNumberTraits = 6;
    private float[] mTraitValue = new float[mNumberTraits];
    private String[] mSocialTraits = new String[mNumberTraits];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_imprssion_face);

        final ScrollView mScrollView = (ScrollView) findViewById(R.id.predict_impression_face_scroll_view);
        // the UPLOAD button
        mUploadButton = (Button) findViewById(R.id.action_upload_photo_predict_impression_button);
        // the PredictImpression Button
        mPredictImpressionButton = (Button) findViewById(R.id.action_predict_impression_face);
        // The Bar Chart
        mBarChart = (BarChart) findViewById(R.id.predict_impression_face_bar_chart);


        // The URL is used to test
        // String imageUrl = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";
        // ImageHelper.ImageLoad(PredictImpressionFaceActivity.this, imageUrl, mFaceImageView);

        // Upload and show face image
        mFaceImageView = (ImageView) findViewById(R.id.show_face_image_prediction_impression);
        File internalStorage = PredictImpressionFaceActivity.this.getDir(AVUser.getCurrentUser().getUsername(), MODE_PRIVATE);
        File myImage = new File(internalStorage.getPath(), "predictImage");
        if (myImage.exists() && myImage.length() > 0) {
            GlideApp.with(PredictImpressionFaceActivity.this)
                    .load(myImage)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.face_image)
                    .error(R.drawable.face_image)
                    .into(mFaceImageView);

            mPredictImpressionButton.setEnabled(true);
        } else {
            GlideApp.with(PredictImpressionFaceActivity.this).load(R.drawable.face_image).into(mFaceImageView);
        }


        // on Click the PICK Button
        Button mPickButton = (Button) findViewById(R.id.action_pick_photo_predict_impression_button);
        mPickButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                PredictImpressionFaceActivityPermissionsDispatcher.pickPhotoFromGalleryWithCheck(PredictImpressionFaceActivity.this);
                mUploadButton.setEnabled(true);
            }
        });


        // The Upload Button is enabled after the Pick Button is clicked
        mUploadButton.setEnabled(false);
        // on Click the UPLOAD button
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Copy the uploaded photo from External Storage to Internal Storage
                ImageHelper.copyPhotoToInternalStorage(mImagePath, PredictImpressionFaceActivity.this, "predictImage");

                uploadPhotoToLeanCloud();

                mUploadButton.setEnabled(false);
                mPredictImpressionButton.setEnabled(true);
            }
        });


        // the PredictImpression Button is enabled if the Upload Button is clicked or there exists a face image
        mPredictImpressionButton.setEnabled(true);
        // onClick the PredictImpression Button
        mPredictImpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                // Show the prediction result
                showResult();
            }
        });


        // Set All Spinners
        // onClick Gender spinner
        Spinner mGenderSpinner = (Spinner) findViewById(R.id.action_gender);
        ArrayAdapter mGenderAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_gender_levels, android.R.layout.simple_spinner_item);
        mGenderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(mGenderAdapter);
        mGenderSpinner.setOnItemSelectedListener(this);


        // onClick Gender spinner
        Spinner mAgeSpinner = (Spinner) findViewById(R.id.action_age);
        ArrayAdapter mAgeAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_age_interval, android.R.layout.simple_spinner_item);
        mAgeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mAgeSpinner.setAdapter(mAgeAdapter);
        mAgeSpinner.setOnItemSelectedListener(this);


        // onClick Education spinner
        Spinner mEducationSpinner = (Spinner) findViewById(R.id.action_education);
        ArrayAdapter mEducationAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_education_levels, android.R.layout.simple_spinner_item);
        mEducationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mEducationSpinner.setAdapter(mEducationAdapter);
        mEducationSpinner.setOnItemSelectedListener(this);


        // onClick Occupation spinner
        Spinner mOccupationSpinner = (Spinner) findViewById(R.id.action_occupation);
        ArrayAdapter mOccupationAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_occupations, android.R.layout.simple_spinner_item);
        mOccupationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOccupationSpinner.setAdapter(mOccupationAdapter);
        mOccupationSpinner.setOnItemSelectedListener(this);


        // onClick BirthPlace spinner
        Spinner mBirthPlaceSpinner = (Spinner) findViewById(R.id.action_birth_place);
        ArrayAdapter mBirthPlaceAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_place, android.R.layout.simple_spinner_item);
        mBirthPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBirthPlaceSpinner.setAdapter(mBirthPlaceAdapter);
        mBirthPlaceSpinner.setOnItemSelectedListener(this);


        // onClick WorkPlace spinner
        Spinner mWorkPlaceSpinner = (Spinner) findViewById(R.id.action_work_place);
        ArrayAdapter mWorkPlaceAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_place, android.R.layout.simple_spinner_item);
        mWorkPlaceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mWorkPlaceSpinner.setAdapter(mWorkPlaceAdapter);
        mWorkPlaceSpinner.setOnItemSelectedListener(this);


        // onClick Oversea spinner
        Spinner mOverseaSpinner = (Spinner) findViewById(R.id.action_oversea);
        ArrayAdapter mOverseaAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mOverseaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mOverseaSpinner.setAdapter(mOverseaAdapter);
        mOverseaSpinner.setOnItemSelectedListener(this);


        // onClick Single spinner
        Spinner mSingleSpinner = (Spinner) findViewById(R.id.action_single);
        ArrayAdapter mSingleAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mSingleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSingleSpinner.setAdapter(mSingleAdapter);
        mSingleSpinner.setOnItemSelectedListener(this);


        // onClick Religion spinner
        Spinner mReligionSpinner = (Spinner) findViewById(R.id.action_religion);
        ArrayAdapter mReligionAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mReligionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReligionSpinner.setAdapter(mReligionAdapter);
        mReligionSpinner.setOnItemSelectedListener(this);


        // onClick Pet spinner
        Spinner mPetSpinner = (Spinner) findViewById(R.id.action_pet);
        ArrayAdapter mPetAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_yes_no, android.R.layout.simple_spinner_item);
        mPetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPetSpinner.setAdapter(mPetAdapter);
        mPetSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()){
            case R.id.action_gender:
                mGenderPosition = position;
                break;

            case R.id.action_age:
                mAgePosition = position;
                break;

            case R.id.action_education:
                mEducationPosition = position;
                break;

            case R.id.action_occupation:
                mOccupationPosition = position;
                break;

            case R.id.action_birth_place:
                mBirthPlacePosition = position;
                break;

            case R.id.action_work_place:
                mWorkPlacePosition = position;
                break;

            case R.id.action_oversea:
                mOverseaPosition = position;
                break;

            case R.id.action_single:
                mSinglePosition = position;
                break;

            case R.id.action_religion:
                mReligionPosition = position;
                break;

            case R.id.action_pet:
                mPetPosition = position;
                break;
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CODE_IMAGE) {
            if(data == null) {
                return;
            }

            // Show the Picked image on the imageView
            GlideApp.with(this).load(data.getData()).into(mFaceImageView);

            // Get the real path of the Picked image from its Uri
            mImagePath = ImageHelper.getRealPathFromURI(PredictImpressionFaceActivity.this, data.getData());
        }
    }


    // Pick Photo from the Phone's gallery
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void pickPhotoFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, null);
        photoPickerIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_IMAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: Delegate the permission handling to generated method
        PredictImpressionFaceActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    // Upload the picked photo to LeanCloud
    private void uploadPhotoToLeanCloud() {
        try {
            mImageFile = AVFile.withAbsoluteLocalPath(AVUser.getCurrentUser().getUsername()+".jpg", mImagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // progressBar.setVisibility(View.VISIBLE);

        // Upload the face photo to the LeanCloud
        mImageFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // progressBar.setVisibility(View.GONE);

                    // Get the URL of the uploaded photo
                    String mPhotoUrl = mImageFile.getUrl();

                    // Update the last uploaded face photo to the PredictFacePhoto table (class in LeanCloud)
                    predictFacePhotoId = MyInfoPreference.getStoredPredictFacePhotoId(PredictImpressionFaceActivity.this);

                    if (predictFacePhotoId != null) {
                        final AVObject predictFaceObject = AVObject.createWithoutData("PredictFacePhoto", predictFacePhotoId);
                        predictFaceObject.put("photoUrl", mPhotoUrl);

                        predictFaceObject.saveInBackground();
                    } else {
                        final AVObject predictFaceObject = new AVObject("PredictFacePhoto");
                        predictFaceObject.put("username", AVUser.getCurrentUser().getUsername());
                        predictFaceObject.put("userId", AVUser.getCurrentUser());
                        predictFaceObject.put("photoUrl", mPhotoUrl);

                        predictFaceObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                predictFacePhotoId = predictFaceObject.getObjectId();
                                MyInfoPreference.setStoredPredictFacePhotoId(PredictImpressionFaceActivity.this, predictFacePhotoId);
                            }
                        });
                    }


                    mUploadButton.setText(R.string.upload_face_photo);
                    Toast.makeText(PredictImpressionFaceActivity.this, R.string.uploading_success, Toast.LENGTH_SHORT).show();
                } else {
                    // progressBar.setVisibility(View.GONE);
                    mUploadButton.setText(R.string.upload_face_photo);
                    Toast.makeText(PredictImpressionFaceActivity.this, R.string.uploading_fail, Toast.LENGTH_SHORT).show();
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(final Integer integer) {
                PredictImpressionFaceActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUploadButton.setText("已经上传: " + integer + "%");
                    }
                });
            }
        });
    }


    private void showResult() {
        // TODO
        // Get the Coefficient Estimates from LeanCloud
        AVQuery<AVObject> mQuery = new AVQuery<>("CoefEstimation");
        mQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                float[] coefEst = new float[10];
                String[] mSocialTraitsList = new String[10];
                mSocialTraitsList[0] = "coefGender";
                mSocialTraitsList[1] = "coefAge";
                mSocialTraitsList[2] = "coefEdu";
                mSocialTraitsList[3] = "coefOccupation";
                mSocialTraitsList[4] = "coefBirthPlace";
                mSocialTraitsList[5] = "coefWorkPlace";
                mSocialTraitsList[6] = "coefOversea";
                mSocialTraitsList[7] = "coefSingle";
                mSocialTraitsList[8] = "coefReligion";
                mSocialTraitsList[9] = "coefPet";

                int i = 0;
                for (AVObject object : list) {
                    mSocialTraits[i] = object.getString("socialTraitCn");

                    for (int j = 0; j < 10; j++) {
                        coefEst[j] = Float.valueOf(object.getString(mSocialTraitsList[j]));
                    }

                    mTraitValue[i] = coefEst[0] * mGenderPosition + coefEst[1] * mAgePosition + coefEst[2] * mEducationPosition + coefEst[3] * mOccupationPosition
                            + coefEst[4] * mBirthPlacePosition + coefEst[5] * mWorkPlacePosition + coefEst[6] * mOverseaPosition + coefEst[7] * mSinglePosition
                            + coefEst[8] * mReligionPosition + coefEst[9] * mPetPosition;

                    i++;
                }

                plotBarChart();
            }
        });
    }


    // Plot the BarChart of prediction result
    private void plotBarChart() {
        // The labels that will be drawn on the XAxis
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mSocialTraits[(int) value];
            }
        };
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxis.setLabelCount(mNumberQuestions);
        xAxis.setValueFormatter(formatter);


        // Get Estimates
        List<BarEntry> barEntries = new ArrayList<>();
        for (int i=0; i<mNumberTraits; i++) {
            barEntries.add(new BarEntry(i, mTraitValue[i]));
        }

        // Plot the Figure
        BarDataSet barDataSet = new BarDataSet(barEntries, "TA对我的第一印象");
        BarData theData = new BarData(barDataSet);
        mBarChart.setDescription(null);
        // mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }
}
