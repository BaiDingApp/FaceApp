package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.baidingapp.faceapp.helper.ImageHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class PredictImpressionFaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_imprssion_face);

        final ScrollView mScrollView = (ScrollView) findViewById(R.id.predict_impression_face_scroll_view);


        // Upload and show face image
        ImageView mFaceImageView = (ImageView) findViewById(R.id.action_upload_photo_prediction_impression);

        // The URL is used to test Glide
        String imageUrl = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";

        ImageHelper.ImageLoad(PredictImpressionFaceActivity.this, imageUrl, mFaceImageView);


        Button mPredictImpressionButton = (Button) findViewById(R.id.action_predict_impression_face);
        mPredictImpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                // Show the rate by others
                showResult();
            }
        });
    }

    private void showResult() {
        // Plot the rates by others
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
