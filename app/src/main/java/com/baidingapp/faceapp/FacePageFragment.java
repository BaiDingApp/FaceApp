package com.baidingapp.faceapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
                // TODO
                Toast.makeText(getActivity(),"Hi, 读人", Toast.LENGTH_SHORT).show();
            }
        });

        Button mOutputRateButton = (Button) view.findViewById(R.id.action_output_rate);
        mOutputRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(),"Hi, 别人眼中的我", Toast.LENGTH_SHORT).show();
            }
        });

        Button mPredictImpressionButton = (Button) view.findViewById(R.id.action_predict_impression);
        mPredictImpressionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                Toast.makeText(getActivity(),"Hi, 预测第一印象", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
