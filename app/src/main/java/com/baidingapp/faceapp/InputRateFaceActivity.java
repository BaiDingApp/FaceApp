package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.baidingapp.faceapp.helper.ImageHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class InputRateFaceActivity extends AppCompatActivity {

    private ScrollView mScrollView;
    private RadioGroup mRadioGroup;
    private ArrayList<String> imageUrlList;
    private int mImageUrlIndex = 0;

    private ImageView mFaceImageView;
    private BarChart mBarChart;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_rate_face);

        mScrollView = (ScrollView) findViewById(R.id.input_rate_scroll_view);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group_input_rate_face);


        // Just for testing
        imageUrlList = new ArrayList<>();
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744927934022586120687.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744866697126501620226.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744858711401934485062.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744893747816378981865.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744881214642035467167.jpg");
        imageUrlList.add("http://www.fdsm.fudan.edu.cn/UserWebEditorUploadImage/upload/image/20160428/6359744868010661201111709.jpg");
        imageUrlList.add("http://bus.sysu.edu.cn/uploads/Head/201101/201101070814308595.jpg");

        // Upload and show face image
        mFaceImageView = (ImageView) findViewById(R.id.face_image_input_rate);
        ImageHelper.ImageLoad(InputRateFaceActivity.this, imageUrlList.get(mImageUrlIndex), mFaceImageView);


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
                showResult();
            }
        });


        mSpinner = (Spinner) findViewById(R.id.action_guess_occupations);
        // onCLick the NEXT button
        Button mNextButton = (Button) findViewById(R.id.action_show_next);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateFaceImage();
            }
        });

    }


    private void updateFaceImage() {
        mRadioGroup.clearCheck();
        mBarChart.clear();
        mSpinner.setSelection(0);

        // Reset a new face image
        mScrollView.fullScroll(ScrollView.FOCUS_UP);
        mImageUrlIndex = mImageUrlIndex + 1;
        ImageHelper.ImageLoad(InputRateFaceActivity.this, imageUrlList.get(mImageUrlIndex), mFaceImageView);
    }


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

        BarDataSet barDataSet = new BarDataSet(barEntries, "别人眼中的我");
        BarData theData = new BarData(barDataSet);
        mBarChart.setDescription(null);
        mBarChart.setData(theData);
        mBarChart.animateY(1000);
        mBarChart.invalidate();
    }

}
