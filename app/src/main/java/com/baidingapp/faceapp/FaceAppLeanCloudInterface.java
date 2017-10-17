package com.baidingapp.faceapp;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

import cn.leancloud.chatkit.LCChatKit;

public class FaceAppLeanCloudInterface extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        final String APP_ID = "KPFub1Cqx5XhELXhhG0lFCUw-gzGzoHsz";
        final String APP_KEY = "BDM4MXPLdWHqyEwsNlIyP0ca";

        // Initialize LeanCloud with Parameters: this, AppId, AppKey
        AVOSCloud.initialize(this, APP_ID, APP_KEY);

        // Initialize LeanCloud Chat Kit
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);


        // Open the DebugLog of LeanCloud
        // Should be CLOSED before publishing the App
        AVOSCloud.setDebugLogEnabled(true);
    }
}