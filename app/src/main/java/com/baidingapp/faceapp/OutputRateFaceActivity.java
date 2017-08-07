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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.ImageHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class OutputRateFaceActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 1;

    private String mImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_rate_face);


        // Upload and show face image
        ImageView mFaceImageView = (ImageView) findViewById(R.id.face_image_output_rate);
        mFaceImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                OutputRateFaceActivityPermissionsDispatcher.pickPhotoFromGalleryWithCheck(OutputRateFaceActivity.this);
            }
        });


        Button mUploadButton = (Button) findViewById(R.id.action_upload_face_photo_button);
        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhotoToLeanCloud();
            }
        });


        // The URL is used to test Glide
        String imageUrl = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";

        ImageHelper.ImageLoad(OutputRateFaceActivity.this, imageUrl, mFaceImageView);


        // Show the rate by others
        showResult();
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

            mImagePath = getRealPathFromURI(OutputRateFaceActivity.this, data.getData());

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


    // Upload Photo to LeanCloud
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


    private void uploadPhotoToLeanCloud() {
        AVFile mImageFile = null;
        try {
            mImageFile = AVFile.withAbsoluteLocalPath(AVUser.getCurrentUser().toString()+".jpg", mImagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
        AVObject photoInfo = new AVObject("PhotoInfo");
        photoInfo.put("username", AVUser.getCurrentUser());
        photoInfo.put("photo", mImageFile);
        */

        mImageFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    Log.d("saved", "文件上传成功！");
                } else {
                    Log.d("saved", "文件上传失败! " + e.getMessage() + "|" + e.getCause());
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer integer) {
                // Uploading Progress Indicator，an integer between 0 and 100。
            }
        });
    }


    private void showResult() {
        // Plot the rates by others
        BarChart mBarChart = (BarChart) findViewById(R.id.output_rate_bar_chart);

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

        BarDataSet barDataSet = new BarDataSet(barEntries, "别人眼中的我");
        BarData theData = new BarData(barDataSet);
        mBarChart.setDescription(null);
        // mBarChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }
}
