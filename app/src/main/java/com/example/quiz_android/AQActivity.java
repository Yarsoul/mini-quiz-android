package com.example.quiz_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class AQActivity extends AppCompatActivity {
    public static final String REQUEST_KEY_1 = "REQUEST_KEY_1";
    public static final String REQUEST_KEY_2 = "REQUEST_KEY_2";
    public static final String REQUEST_KEY_3 = "REQUEST_KEY_3";
    public static final String SCORE_KEY = "SCORE";
    public static final String SCORES_ALL_KEY = "SCORES_ALL";
    public static final int AMOUNT_QUESTIONS = 3;
    private static int flag = 0;
    private int buttonSwitcher = 0;
    ArrayList<Integer> scoresList = new ArrayList<>();
    FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
    }

    @Override
    public void onBackPressed() {
        ProgressFragment.timer.cancel();
        super.onBackPressed();
    }


    public static void setFlag(int flag) {
        AQActivity.flag = flag;
    }


    private void addScores(String requestKey, int index) {
        fragmentManager.setFragmentResultListener(requestKey, this,
                (requestKey1, bundle) -> {
                    int result = bundle.getInt(SCORE_KEY);
                    scoresList.add(index, result);
                });
    }


    private boolean isSelected() {
        String textNotSelected = getResources().getString(R.string.text_not_selected);

        if (flag == 1) {
            return true;
        } else {
            Toast.makeText(this, textNotSelected, Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void goNextFragment(Class<? extends Fragment> fragmentClass) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragmentClass, null)
                .setReorderingAllowed(true)
                .commitNow();
    }


    private void goFinish() {
        Intent i = new Intent(this, FinishActivity.class);
        i.putExtra(SCORES_ALL_KEY, scoresList);
        startActivity(i);
        finish();
    }


    public void onNext(View view) {
        switch (buttonSwitcher) {
            case 0:
                if (isSelected()) {
                    buttonSwitcher = 1;
                    addScores(REQUEST_KEY_1, 0);
                    goNextFragment(QuestionFragmentSecond.class);
                }
                break;
            case 1:
                if (isSelected()) {
                    buttonSwitcher = 2;
                    addScores(REQUEST_KEY_2, 1);
                    goNextFragment(QuestionFragmentThird.class);
                }
                break;
            case 2:
                if (isSelected()) {
                    addScores(REQUEST_KEY_3, 2);
                    ProgressFragment.timer.cancel();
                    goFinish();
                }
                break;
        }
    }
}