package com.baidingapp.faceapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FacePageFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_face_page, container, false);

        Button mInputRateButton = (Button) view.findViewById(R.id.action_input_rate);
        mInputRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InputRateFaceActivity.class);
                startActivity(intent);
            }
        });

        Button mOutputRateButton = (Button) view.findViewById(R.id.action_output_rate);
        mOutputRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OutputRateFaceActivity.class);
                startActivity(intent);
            }
        });

        Button mPredictImpressionButton = (Button) view.findViewById(R.id.action_predict_impression_button);
        mPredictImpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PredictImpressionFaceActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
