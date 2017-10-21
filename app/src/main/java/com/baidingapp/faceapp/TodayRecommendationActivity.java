package com.baidingapp.faceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_recommendation);


        // Get the Username of the Current User
        mCurrentUsername = AVUser.getCurrentUser().getUsername();


        // Get the Username of the Matched User
        AVQuery<AVObject> avQuery = new AVQuery<>("MatchedResults");
        avQuery.whereEqualTo("CurrentUser", mCurrentUsername);
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                mMatchedUsername = list.get(0).getString("MatchedUser");
            }
        });


        Button mChatButton = (Button) findViewById(R.id.action_chat_with_ta);
        mChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TodayRecommendationActivity.this, mMatchedUsername, Toast.LENGTH_SHORT).show();
/*
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
*/
            }
        });


        Button mNotInterestedButton = (Button) findViewById(R.id.action_not_interested_in_ta);
        mNotInterestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(TodayRecommendationActivity.this,
                        "Save the result to the database and make new recommendation", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
