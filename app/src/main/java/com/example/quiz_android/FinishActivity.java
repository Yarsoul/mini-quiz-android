package com.example.quiz_android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.quiz_android.placeholder.PlaceholderContent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class FinishActivity extends AppCompatActivity {
    private final static String FILE_NAME = "results.txt";
    private int flag = 0;
    TextView textView;
    FragmentManager fragmentManager;
    File file, fPath;
    String[] arrText = new String[0];
    String strScores, resultsAmount = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        textView = findViewById(R.id.textViewFinish);
        fragmentManager = getSupportFragmentManager();
        String congratulationsFinish = getResources().getString(R.string.congratulations_finish);
        textView.setText(congratulationsFinish);
        fPath = this.getFilesDir();
        file = new File(fPath, FILE_NAME);

        treatmentBundle();
        saveFile(getResultText());
    }


    private void treatmentBundle() {
        String congratulationsAll = getResources().getString(R.string.congratulations_all);
        String correctAnswers = getResources().getString(R.string.correct_answers);
        String textFrom = getResources().getString(R.string.from);
        Bundle bundle = getIntent().getExtras();

        if (bundle.get(AQActivity.SCORES_ALL_KEY) != null) {
            ArrayList<Integer> scoresList = bundle.getIntegerArrayList(AQActivity.SCORES_ALL_KEY);
            int sumScores = 0;
            for (int i = 0; i < scoresList.size(); ++i)
                sumScores += scoresList.get(i);
            strScores = String.valueOf(sumScores);
            textView.append(correctAnswers + " " + strScores + " " + textFrom + " "
                    + AQActivity.AMOUNT_QUESTIONS + ".");
            resultsAmount = strScores + " " + textFrom + " " + AQActivity.AMOUNT_QUESTIONS;
            if (sumScores == AQActivity.AMOUNT_QUESTIONS) {
                textView.append(congratulationsAll);
            }
        }

        if (bundle.get("timeout") != null) {
            resultsAmount = resultsAmount + " (" + getResources().getString(R.string.time_out) + ")";
            textView.setText(getResources().getString(R.string.time_out_again));
        }
    }


    @NonNull
    private String getResultText() {
        String dateText = getDate();
        String text = dateText + "\n" + getResources().getString(R.string.result) + " "
                + resultsAmount + ".\n\n";
        return text;
    }


    private String getDate() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);
        String textDate = dateText + " (" + timeText + ") ";
        return textDate;
    }


    private void addContentFromArrText() {
        for (int i = 0; i < arrText.length; i++) {
            String strId = String.valueOf(i + 1);
            PlaceholderContent.ITEMS.add(new PlaceholderContent.PlaceholderItem(strId, arrText[i]));
        }
    }


    private void createEmptyFile() {
        try {
            FileOutputStream fileOutput = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fileOutput.close();

            Log.d(MainActivity.LOG_TAG, getResources().getString(R.string.file_save_empty));

        } catch (IOException e) {
            Log.e(MainActivity.LOG_TAG, e.getMessage());
            Toast.makeText(this, getResources().getString(R.string.file_save_error),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void saveFile(@NonNull String resultText) {
        try {
            FileOutputStream fileOutput = openFileOutput(FILE_NAME, MODE_APPEND);
            fileOutput.write(resultText.getBytes());
            fileOutput.close();

            Log.d(MainActivity.LOG_TAG, getResources().getString(R.string.file_save));

        } catch (IOException e) {
            Log.e(MainActivity.LOG_TAG, e.getMessage());
            Toast.makeText(this, getResources().getString(R.string.file_save_error),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void readFile() {
        if (file.exists()) {
            try {
                FileInputStream fileInput = openFileInput(FILE_NAME);
                byte[] arrBytes = new byte[fileInput.available()];
                int countBytes = fileInput.read(arrBytes);


                if (countBytes != 0) {
                    String text = new String(arrBytes);
                    arrText = text.split("\n\n");
                } else {
                    arrText = new String[0];
                    String clearHistory = getResources().getString(R.string.clear_history);
                    PlaceholderContent.ITEMS.clear();
                    PlaceholderContent.ITEMS.add(new PlaceholderContent.PlaceholderItem(
                            "", clearHistory));
                }

                Log.d(MainActivity.LOG_TAG, getResources().getString(R.string.file_bytes) + " "
                                + countBytes);
                fileInput.close();

                Log.d(MainActivity.LOG_TAG, getResources().getString(R.string.file_read));

            } catch (IllegalStateException | IOException e) {
                Toast.makeText(this, getResources().getString(R.string.file_read_error),
                        Toast.LENGTH_SHORT).show();
                Log.e(MainActivity.LOG_TAG, e.getMessage());
            }
        } else {
            Log.d(MainActivity.LOG_TAG, getResources().getString(R.string.file_not_exist));
            Toast.makeText(this, getResources().getString(R.string.file_not_exist),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void showFragmentResults() {
        if (!isAddedFragment()) {
            ResultsFragment myFragment = new ResultsFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainerView2, myFragment)
                    .setReorderingAllowed(true)
                    .commit();
        } else {
            setVisibleResults(Objects.requireNonNull(fragmentManager
                    .findFragmentById(R.id.fragmentContainerView2)).isHidden());
        }

        setVisibleText(textView.getVisibility());
        setButtonText(flag);
    }


    private void setVisibleResults(@NonNull Boolean bool) {
        Fragment fragment = Objects.requireNonNull(fragmentManager
                .findFragmentById(R.id.fragmentContainerView2));

        if (bool) {
            fragmentManager.beginTransaction()
                    .attach(fragment)
                    .show(fragment)
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .hide(fragment)
                    .detach(fragment)
                    .commit();
        }
    }


    private void setVisibleText(int visibility) {
        if (visibility == View.VISIBLE) {
            textView.setVisibility(View.INVISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }


    private void setButtonText(int flag) {
        Button button = findViewById(R.id.buttonShowHistory);
        if (flag == 1) {
            button.setText(getResources().getString(R.string.button_show_history));
            this.flag = 0;
        } else {
            button.setText(getResources().getString(R.string.button_hide_history));
            this.flag = 1;
        }
    }


    private boolean isAddedFragment() {
        return fragmentManager.findFragmentById(R.id.fragmentContainerView2) != null;
    }


    public void onToStart(View view) {
        if (isAddedFragment()) {
            fragmentManager.beginTransaction()
                    .remove(Objects.requireNonNull(fragmentManager
                            .findFragmentById(R.id.fragmentContainerView2)))
                    .commit();
        }
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }


    public void onShowResults(View view) {
        PlaceholderContent.ITEMS.clear();
        readFile();
        addContentFromArrText();
        getResultText();
        showFragmentResults();
    }


    public void onClearHistory(View view) {
        createEmptyFile();
        if (textView.getVisibility() == View.INVISIBLE) {
            showFragmentResults();
        }

        Toast.makeText(this, getResources().getString(R.string.clear_history),
                Toast.LENGTH_SHORT).show();
        Log.d(MainActivity.LOG_TAG, getResources().getString(R.string.clear_history));
    }
}