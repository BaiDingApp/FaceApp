package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class InputRateFaceActivity extends AppCompatActivity {

    // The URL is used to test Glide
    private final String imageUrl2 = "http://bus.sysu.edu.cn/uploads/Head/201101/201101070814308595.jpg";


    private ImageView mFaceImageView;
    private BarChart mBarChart;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rate_face);

        final ScrollView mScrollView = (ScrollView) findViewById(R.id.input_rate_scroll_view);
        final RadioGroup mRadioGroup = (RadioGroup) findViewById(R.id.radio_group_input_rate_face);


        // The URL is used to test Glide
        String imageUrl1 = "http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg";
        // Upload and show face image
        mFaceImageView = (ImageView) findViewById(R.id.face_image_input_rate);
        ImageHelper.ImageLoad(InputRateFaceActivity.this, imageUrl1, mFaceImageView);


        // Plot the rates by others
        mBarChart = (BarChart) findViewById(R.id.input_rate_bar_chart);
        mBarChart.setNoDataText(getResources().getString(R.string.no_result_available));
        // mBarChart.setNoDataTextColor(R.color.red);

        // onCLick the RESULT button
        Button mResultButton = (Button) findViewById(R.id.action_show_result);
        mResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the results
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                updateResult();
            }
        });


        mSpinner = (Spinner) findViewById(R.id.action_guess_occupations);
        // onCLick the NEXT button
        Button mNextButton = (Button) findViewById(R.id.action_show_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRadioGroup.clearCheck();
                mBarChart.clear();
                mSpinner.setSelection(0);

                // Reset a new face image
                mScrollView.fullScroll(ScrollView.FOCUS_UP);
                ImageHelper.ImageLoad(InputRateFaceActivity.this, imageUrl2, mFaceImageView);
            }
        });

    }


    private void updateResult() {

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
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }

}
