package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

public class TodayRecomIntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_recom_intro);

        // Set title for each expandable text
        ((TextView) findViewById(R.id.today_recom_intro).findViewById(R.id.title)).setText("今日推荐介绍");
        ((TextView) findViewById(R.id.today_recom_rules).findViewById(R.id.title)).setText("今日推荐规则");

        // Set context for each expandable text
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.today_recom_intro)
                .findViewById(R.id.expand_text_view);
        ExpandableTextView expTv2 = (ExpandableTextView) findViewById(R.id.today_recom_rules)
                .findViewById(R.id.expand_text_view);
        expTv1.setText(getString(R.string.today_recom_intro));
        expTv2.setText(getString(R.string.today_recom_rules));
    }
}
