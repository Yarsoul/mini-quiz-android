package com.example.quiz_android;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public interface Gettable {

    default String getQuestionFromDb(Activity activity, View view, int numberOfQuestion) {
        DBHelper dbHelper = new DBHelper(activity);
        String question = "";

        try {
            SQLiteDatabase sqLiteDatabase = activity.getBaseContext()
                    .openOrCreateDatabase(DBHelper.MY_DB, Context.MODE_PRIVATE, null);
            dbHelper.onCreate(sqLiteDatabase);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_QUESTIONS
                            + " WHERE number = " + numberOfQuestion + ";",
                    null);

            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                question = cursor.getString(1);
            }

            cursor.close();
            sqLiteDatabase.close();

            Log.d(MainActivity.LOG_TAG, activity.getResources().getString(R.string.db_read));

        } catch (SQLException | NullPointerException e) {
            Toast.makeText(view.getContext(),
                    activity.getResources().getString(R.string.db_read_error),
                    Toast.LENGTH_SHORT).show();
            Log.d(MainActivity.LOG_TAG, activity.getResources().getString(R.string.db_read_error));
            Log.e(MainActivity.LOG_TAG, e.getMessage());
        }

        return question;
    }


    default String[] getAnswersFromDb(Activity activity, View view, int numberOfQuestion, int amountAnswers) {
        DBHelper dbHelper = new DBHelper(activity);
        ArrayList<String> listAnswers = new ArrayList<>(amountAnswers);

        try {
            SQLiteDatabase sqLiteDatabase = activity.getBaseContext()
                    .openOrCreateDatabase(DBHelper.MY_DB, Context.MODE_PRIVATE, null);
            dbHelper.onCreate(sqLiteDatabase);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + DBHelper.TABLE_ANSWERS
                            + " WHERE numQuestion = " + numberOfQuestion + ";",
                    null);

            if (cursor.moveToFirst()) {
                do {
                    String answer = cursor.getString(1);
                    listAnswers.add(answer);
                } while (cursor.moveToNext());
            }

            cursor.close();
            sqLiteDatabase.close();

            Log.d(MainActivity.LOG_TAG, activity.getResources().getString(R.string.db_read));

        } catch (SQLException e) {
            Toast.makeText(view.getContext(),
                    activity.getResources().getString(R.string.db_read_error),
                    Toast.LENGTH_SHORT).show();
            Log.e(MainActivity.LOG_TAG, activity.getResources().getString(R.string.db_read_error));
        }

        final String[] strings = listAnswers.toArray(new String[amountAnswers]);

        return strings;
    }
}
