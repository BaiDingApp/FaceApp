package com.baidingapp.faceapp;

import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.avos.avoscloud.AVUser;

public class MePageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_page, container, false);


        Button mMyInformationButton = (Button) view.findViewById(R.id.action_my_information);
        mMyInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BasicInformationDefaultActivity.class);
                startActivity(intent);
            }
        });


        Button mLogoutButton = (Button) view.findViewById(R.id.action_logout);
        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AVUser.getCurrentUser().logOut();
                Intent intent = new Intent(getActivity(), LauncherPageActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });


        return view;
    }
}