package com.example.quiz_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


public class ProgressFragment extends Fragment {
    ProgressBar progressBar;
    TextView textView;
    Activity activity;
    static CountDownTimer timer;
    static Long time = 0L;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.textStatus);
        activity = getActivity();

        timer = new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
                Long count = millisUntilFinished / 1000;
                String strCount = String.valueOf(count);
                textView.setText(strCount);
            }

            public void onFinish() {
                Intent i = new Intent(activity, FinishActivity.class);
                i.putExtra("timeout", 0);
                activity.startActivity(i);
            }
        }.start();

        return view;
    }
}