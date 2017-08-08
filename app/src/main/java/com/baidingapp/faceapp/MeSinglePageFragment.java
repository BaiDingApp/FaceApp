package com.baidingapp.faceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;

public class MeSinglePageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_single_page, container, false);


        ImageView mChatRoomImage = (ImageView) view.findViewById(R.id.action_enter_chatroom);
        mChatRoomImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(),"进入聊天室", Toast.LENGTH_SHORT).show();
            }
        });


        Button mOpenRecommendationButton = (Button) view.findViewById(R.id.action_open_recommendation);
        mOpenRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(),"开通——今日推荐", Toast.LENGTH_SHORT).show();
            }
        });


        Button mCloseRecommendationButton = (Button) view.findViewById(R.id.action_close_recommendation);
        mCloseRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(),"暂停——今日推荐", Toast.LENGTH_SHORT).show();
            }
        });


        Button mIntroductionRecommendationButton = (Button) view.findViewById(R.id.action_introduction_recommendation);
        mIntroductionRecommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TodayRecomIntroActivity.class);
                startActivity(intent);
            }
        });


        Button mMyInformationButton = (Button) view.findViewById(R.id.action_my_information_single);
        mMyInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MyInformationSingleActivity.class);
                startActivity(intent);
            }
        });


        Button mAllOtherRateButton = (Button) view.findViewById(R.id.action_all_other_rate_single);
        mAllOtherRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(),"历轮别人眼中的我（单身）", Toast.LENGTH_SHORT).show();
            }
        });


        Button mLogoutButton = (Button) view.findViewById(R.id.action_logout_single);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser.getCurrentUser().logOut();
                getActivity().finish();
                Intent intent = new Intent(getActivity(), LauncherPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        return view;
    }
}
