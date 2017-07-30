package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

public class PredictImpressionFaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_imprssion_face);

        final ScrollView mScrollView = (ScrollView) findViewById(R.id.predict_impression_face_scroll_view);

        Button mPredictImpressionButton = (Button) findViewById(R.id.action_predict_impression_face);
        mPredictImpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                // TODO
            }
        });
    }
}
