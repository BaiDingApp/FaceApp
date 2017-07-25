package com.baidingapp.faceapp;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by zhouyh on 17/7/24.
 */

public class FaceAppLeanCloudInterface extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize LeanCloud with Parameters: this, AppId, AppKey
        AVOSCloud.initialize(this,"KPFub1Cqx5XhELXhhG0lFCUw-gzGzoHsz","BDM4MXPLdWHqyEwsNlIyP0ca");

        // Open the DebugLog of LeanCloud
        // Should be CLOSED before publishing the App
        AVOSCloud.setDebugLogEnabled(true);
    }
}