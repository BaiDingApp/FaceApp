package com.baidingapp.faceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.CloudQueryCallback;

import java.util.ArrayList;
import java.util.List;

public class AllInputRateFaceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> mRatedPhotosUrlList = new ArrayList<>();
    List<AllInputRateModel> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_input_rate_face);

        // Initialize RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.all_input_rate_recycler_view);

        // Get mRatedPhotosUrlList and Show the RecyclerView
        getUrlOfRatedFacePhotosAndShowRV();
    }


    // Get photoUrl of the Rated Photos in the RateFacePhoto Table
    // Use CQL query method
    private void getUrlOfRatedFacePhotosAndShowRV() {
        String currUsername = AVUser.getCurrentUser().getUsername();
        String photoNotRated = "select * from RateFacePhoto where username = (select usernameRated from RateFaceScore where usernameRating = ? limit 1000) limit 1000";

        AVQuery.doCloudQueryInBackground(photoNotRated, new CloudQueryCallback<AVCloudQueryResult>() {
            @Override
            public void done(AVCloudQueryResult avCloudQueryResult, AVException e) {
                for (AVObject photo : (avCloudQueryResult.getResults())) {
                    mRatedPhotosUrlList.add(photo.getString("photoUrl"));
                }

                // Get Data
                AllInputRateModel model;
                for (int i=1; i<=mRatedPhotosUrlList.size(); i++) {
                    model = new AllInputRateModel();
                    model.setNumber("第" + i + "张");
                    model.setPhotoUrl(mRatedPhotosUrlList.get(i-1));
                    data.add(model);
                }

                // Create the LayoutManager
                LinearLayoutManager layoutManager = new LinearLayoutManager(AllInputRateFaceActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);

                // Create the Adapter
                AllInputRateAdapter adapter = new AllInputRateAdapter(R.layout.item_all_input_rate, data);
                adapter.openLoadAnimation();

                // Set Adapter for the RecyclerView
                recyclerView.setAdapter(adapter);
            }
        }, currUsername);
    }

}
