package com.baidingapp.faceapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.ImageHelper;
import com.baidingapp.faceapp.helper.MyInfoPreference;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PredictImpressionFaceActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 2;

    private ImageView mFaceImageView;
    private Button mUploadButton;
    private String mImagePath;
    private AVFile mImageFile = null;
    private String predictFacePhotoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_imprssion_face);

        final ScrollView mScrollView = (ScrollView) findViewById(R.id.predict_impression_face_scroll_view);


        // The URL is used to test
        // String imageUrl = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";
        // ImageHelper.ImageLoad(PredictImpressionFaceActivity.this, imageUrl, mFaceImageView);

        // Upload and show face image
        mFaceImageView = (ImageView) findViewById(R.id.action_uploaded_photo_prediction_impression);
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
        } else {
            GlideApp.with(PredictImpressionFaceActivity.this).load(R.drawable.face_image).into(mFaceImageView);
        }


        // on Click the UPLOAD button
        mUploadButton = (Button) findViewById(R.id.action_upload_photo_predict_impression_button);

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

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhotoToLeanCloud();
                copyPhotoToInternalStorage();
                mUploadButton.setEnabled(false);
            }
        });


        Button mPredictImpressionButton = (Button) findViewById(R.id.action_predict_impression_face);
        mPredictImpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                // Show the prediction result
                showResult();
            }
        });
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
            mImagePath = getRealPathFromURI(PredictImpressionFaceActivity.this, data.getData());
        }
    }


    // Get the real path of the file from its Uri
    private String getRealPathFromURI(Context context, Uri contentUri) {
        if (contentUri.getScheme().equals("file")) {
            return contentUri.getPath();
        } else {
            Cursor cursor = null;
            try {
                String[] mDataString = {MediaStore.Images.Media.DATA};
                cursor = context.getContentResolver().query(contentUri, mDataString, null, null, null);
                if (null != cursor) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    return cursor.getString(column_index);
                } else {
                    return "";
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
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


    private File copyPhotoToInternalStorage() {
        File externalImage = new File(mImagePath);

        // File internalImage = new File(PredictImpressionFaceActivity.this.getFilesDir(), "predictImage.jpg");
        File internalStorage = PredictImpressionFaceActivity.this.getDir(AVUser.getCurrentUser().getUsername(), MODE_PRIVATE);
        File internalImage = new File(internalStorage.getPath(), "predictImage");

        try {
            ImageHelper.copyImage(externalImage, internalImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return internalImage;
    }






    private void showResult() {
        // Plot the BarChart of prediction result
        BarChart mBarChart = (BarChart) findViewById(R.id.predict_impression_face_bar_chart);

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

        BarDataSet barDataSet = new BarDataSet(barEntries, "TA对我的第一印象");
        BarData theData = new BarData(barDataSet);
        mBarChart.setDescription(null);
        // mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }
}
