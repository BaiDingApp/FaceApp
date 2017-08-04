package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.baidingapp.faceapp.helper.ImageHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class OutputRateFaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_rate_face);


        // Upload and show face image
        ImageView mFaceImageView = (ImageView) findViewById(R.id.action_upload_photo_output_rate);

        // The URL is used to test Glide
        String imageUrl = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";

        ImageHelper.ImageLoad(OutputRateFaceActivity.this, imageUrl, mFaceImageView);


        // Show the rate by others
        showResult();

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
