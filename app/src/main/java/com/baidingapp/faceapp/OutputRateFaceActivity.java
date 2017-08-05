package com.baidingapp.faceapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;
import com.baidingapp.faceapp.helper.ImageHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class OutputRateFaceActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_IMAGE = 1;

    private String mImageName;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_rate_face);


        // Upload and show face image
        ImageView mFaceImageView = (ImageView) findViewById(R.id.action_upload_photo_output_rate);
        mFaceImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);


                AVFile mImageFile = null;
                try {
                    mImageFile = AVFile.withFile(mImageName, file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


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
                        // 上传进度数据，integer 介于 0 和 100。
                    }
                });

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
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CODE_IMAGE) {
            if(data == null) {
                return;
            }

            Uri imageUri = data.getData();
            String[] imagePathSplit = imageUri.getPath().split(":");

            String mImagePath;
            if(imagePathSplit.length == 2) {
                // High version
                String relativeImagePath = imagePathSplit[1];
                mImagePath = Environment.getExternalStorageDirectory() +"/" + relativeImagePath;
            } else {
                // Low version
                mImagePath = imageUri.getPath();
            }

            try {
                file = new File(new URI(mImagePath));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }


            String[] folders = mImagePath.split("/");
            mImageName = folders[folders.length-1];
        }
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
