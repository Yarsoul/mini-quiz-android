package com.example.quiz_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    final static String LOG_TAG = "my_logs";
    Spinner spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinnerDifficulty);
        String[] difficult = getResources().getStringArray(R.array.difficulty_levels);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, difficult);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case (0):
                        ProgressFragment.time = 10000L;
                        break;
                    case (1):
                        ProgressFragment.time = 20000L;
                        break;
                    case (2):
                        ProgressFragment.time = 30000L;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        spinner.setSelection(0);
    }


    public void goToFirst(View view) {
        Intent i = new Intent(this, AQActivity.class);
        startActivity(i);
    }
}