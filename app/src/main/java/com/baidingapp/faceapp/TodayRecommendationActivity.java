package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;

import java.util.List;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;

public class TodayRecommendationActivity extends AppCompatActivity {

    private String mCurrentUsername;
    private String mMatchedUsername;
    private TextView mShowMatchText;
    private Button mChatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_recommendation);


        // Show the Username of Today's Match
        mShowMatchText = (TextView) findViewById(R.id.action_show_the_match);


        // Get the Username of the Current User
        mCurrentUsername = AVUser.getCurrentUser().getUsername();


        // Get the Username of the Matched User
        AVQuery<AVObject> avQuery = new AVQuery<>("MatchedResults");
        avQuery.whereEqualTo("CurrentUser", mCurrentUsername);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                mMatchedUsername = list.get(0).getString("MatchedUser");

                // Set the Matched Username
                // Make sure that the "mMatchedUsername" is queried and sent back to the App
                // This is why .setText is put here
                String mMatchInfo = "今日推荐：" + mMatchedUsername;
                mShowMatchText.setText(mMatchInfo);

                // Make sure that the "mMatchedUsername" is queried and sent back to the App
                mChatButton.setEnabled(true);
            }
        });


        // onClick the CHAT with TA Button
        mChatButton = (Button) findViewById(R.id.action_chat_with_ta);
        mChatButton.setEnabled(false);
        mChatButton.getBackground().setAlpha(100);
        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(TodayRecommendationActivity.this, mMatchedUsername, Toast.LENGTH_SHORT).show();

                // Open the Dialog
                LCChatKit.getInstance().open(mCurrentUsername, new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                        if (null == e) {
                            // finish();
                            Intent intent = new Intent(TodayRecommendationActivity.this, LCIMConversationActivity.class);
                            intent.putExtra(LCIMConstants.PEER_ID, mMatchedUsername);
                            startActivity(intent);
                        } else {
                            Toast.makeText(TodayRecommendationActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });


        // onClick the NOT Interested in TA Button
        Button mNotInterestedButton = (Button) findViewById(R.id.action_not_interested_in_ta);
        mNotInterestedButton.getBackground().setAlpha(100);
        mNotInterestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TodayRecommendationActivity.this,
                        "Save the result to the database and make new recommendation", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
