package com.baidingapp.faceapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
// import android.widget.ProgressBar;
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
import com.baidingapp.faceapp.helper.StatHelper;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class OutputRateFaceActivity extends AppCompatActivity {

    // private static final String KEY_IMAGE_URI_STRING =
    //         "com.baidingapp.faceapp.Picked_image_uri";

    private static final int REQUEST_CODE_IMAGE = 1;

    private ImageView mFaceImageView;
    private String mImagePath;
    private AVFile mImageFile = null;
    private Button mUploadButton;
    private String rateFacePhotoId;
    private BarChart mBarChart;

    // private ProgressBar progressBar;
    // private Uri mPickedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_rate_face);

        /*
        // If use this, then the app will crash after picking the photo
        if (savedInstanceState != null) {
            mPickedImageUri = Uri.parse(savedInstanceState.getString(IMAGE_URI_STRING));
        }
        */

        // BarChart
        mBarChart = (BarChart) findViewById(R.id.output_rate_bar_chart);
        mBarChart.setNoDataText(getResources().getString(R.string.please_recheck_later));


        // The URL is used to test
        // String imageUrl = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";

        // Initialize the face image view
        mFaceImageView = (ImageView) findViewById(R.id.face_image_output_rate);
        // ImageHelper.ImageLoad(OutputRateFaceActivity.this, null, mFaceImageView);
        // File myImage = new File(OutputRateFaceActivity.this.getFilesDir(), "outputImage.jpg");
        File internalStorage = OutputRateFaceActivity.this.getDir(AVUser.getCurrentUser().getUsername(), MODE_PRIVATE);
        File myImage = new File(internalStorage.getPath(), "outputImage");
        if (myImage.exists() && myImage.length() > 0) {
            GlideApp.with(OutputRateFaceActivity.this)
                    .load(myImage)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.face_image)
                    .error(R.drawable.face_image)
                    .into(mFaceImageView);
        } else {
            GlideApp.with(OutputRateFaceActivity.this).load(R.drawable.face_image).into(mFaceImageView);
        }


        // on Click the UPLOAD button
        mUploadButton = (Button) findViewById(R.id.action_upload_face_photo_button);

        // on Click the PICK Button
        Button mPickButton = (Button) findViewById(R.id.action_pick_face_photo_button);

        mPickButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                OutputRateFaceActivityPermissionsDispatcher.pickPhotoFromGalleryWithCheck(OutputRateFaceActivity.this);
                mUploadButton.setEnabled(true);
            }
        });


        // progressBar = (ProgressBar) findViewById(R.id.uploading_photo_progressBar_output_face);

        // The Upload Button is enabled after the Pick Button is clicked
        mUploadButton.setEnabled(false);

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhotoToLeanCloud();
                // Copy the uploaded photo from External Storage to Internal Storage
                ImageHelper.copyPhotoToInternalStorage(mImagePath, OutputRateFaceActivity.this, "outputImage");
                mUploadButton.setEnabled(false);
            }
        });


        // Get the rate scores from LeanCloud
        // Show the results via BarChart
        rateFacePhotoId = MyInfoPreference.getStoredRateFacePhotoId(OutputRateFaceActivity.this);
        if (rateFacePhotoId != null) {
            getAllRateScoresAndShowResult(rateFacePhotoId);
        } else {
            Toast.makeText(this, R.string.please_upload_photo_first, Toast.LENGTH_SHORT).show();
        }

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

            // Get the Uri of the Picked image
            // mPickedImageUri = data.getData();

            // Show the Picked image on the imageView
            GlideApp.with(this).load(data.getData()).into(mFaceImageView);

            // Get the real path of the Picked image from its Uri
            mImagePath = ImageHelper.getRealPathFromURI(OutputRateFaceActivity.this, data.getData());
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
        OutputRateFaceActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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

                    // Update the last uploaded face photo to the RateFacePhoto table (class in LeanCloud)
                    rateFacePhotoId = MyInfoPreference.getStoredRateFacePhotoId(OutputRateFaceActivity.this);

                    if (rateFacePhotoId != null) {
                        final AVObject rateFaceObject = AVObject.createWithoutData("RateFacePhoto", rateFacePhotoId);
                        rateFaceObject.put("photoUrl", mPhotoUrl);

                        rateFaceObject.saveInBackground();
                    } else {
                        final AVObject rateFaceObject = new AVObject("RateFacePhoto");
                        rateFaceObject.put("username", AVUser.getCurrentUser().getUsername());
                        rateFaceObject.put("userId", AVUser.getCurrentUser());
                        rateFaceObject.put("photoUrl", mPhotoUrl);

                        rateFaceObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(AVException e) {
                                rateFacePhotoId = rateFaceObject.getObjectId();
                                MyInfoPreference.setStoredRateFacePhotoId(OutputRateFaceActivity.this, rateFacePhotoId);
                            }
                        });
                    }


                    mUploadButton.setText(R.string.upload_face_photo);
                    Toast.makeText(OutputRateFaceActivity.this, R.string.uploading_success, Toast.LENGTH_SHORT).show();
                } else {
                    // progressBar.setVisibility(View.GONE);
                    mUploadButton.setText(R.string.upload_face_photo);
                    Toast.makeText(OutputRateFaceActivity.this, R.string.uploading_fail, Toast.LENGTH_SHORT).show();
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(final Integer integer) {
                OutputRateFaceActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUploadButton.setText("已经上传: " + integer + "%");
                    }
                });
            }
        });
    }


    // Get the rate scores from LeanCloud
    // Show the results via BarChart
    private void getAllRateScoresAndShowResult(String rateFacePhotoId) {
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
                }
                // else { Toast.makeText(OutputRateFaceActivity.this, R.string.please_recheck_later, Toast.LENGTH_SHORT).show(); }
            }
        });
    }


    private void plotBarChart(int[] allRateScores) {
        List<BarEntry> barEntries = StatHelper.getBarEntry(allRateScores);
        BarDataSet barDataSet = new BarDataSet(barEntries, "别人眼中的我");
        BarData theData = new BarData(barDataSet);
        mBarChart.setDescription(null);
        // mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }


    /*
    // If use this, then the app will crash after picking the photo
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(KEY_IMAGE_URI_STRING, mPickedImageUri.toString());
    }
    */
}