package com.example.quiz_android;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final String MY_DB = "MY_DB";
    public static final String TABLE_QUESTIONS = "TABLE_QUESTIONS";
    public static final String TABLE_ANSWERS = "TABLE_ANSWERS";
    Context context;

    public DBHelper(Context context) {
        super(context, MY_DB, null, 1);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ANSWERS);
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_QUESTIONS
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, question TEXT, number INTEGER);");
        database.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ANSWERS
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT, answer TEXT, numQuestion INTEGER);");

        insertQuestions(database);
        insertAnswers(database);
    }

    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        //
    }


    public void insertQuestions(SQLiteDatabase database) {
        String[] questions = context.getResources().getStringArray(R.array.questions);
        Cursor cursor = database.query(DBHelper.TABLE_QUESTIONS, null, null,
                null, null, null, null);

        if (cursor.getCount() == 0) {
            for (int i = 1; i < AQActivity.AMOUNT_QUESTIONS + 1; i++) {
                database.execSQL("INSERT INTO "
                        + DBHelper.TABLE_QUESTIONS + "(question, number) VALUES ('"
                        + questions[i - 1] + "', '"
                        + i + "');");
            }
        }
        cursor.close();
    }


    public void insertAnswers(SQLiteDatabase database) {
        String[] answers1 = context.getResources().getStringArray(R.array.answers_1_colors);
        String[] answers2 = context.getResources().getStringArray(R.array.answers_2_decorations);
        String[] answers3 = context.getResources().getStringArray(R.array.answers_3_sides);

        Cursor cursor = database.query(DBHelper.TABLE_ANSWERS, null, null,
                null, null, null, null);

        if (cursor.getCount() == 0) {
            for (String s : answers1) {
                database.execSQL("INSERT INTO "
                        + DBHelper.TABLE_ANSWERS + "(answer, numQuestion) VALUES ('"
                        + s + "', '"
                        + 1 + "');");
            }

            for (String s : answers2) {
                database.execSQL("INSERT INTO "
                        + DBHelper.TABLE_ANSWERS + "(answer, numQuestion) VALUES ('"
                        + s + "', '"
                        + 2 + "');");
            }

            for (String s : answers3) {
                database.execSQL("INSERT INTO "
                        + DBHelper.TABLE_ANSWERS + "(answer, numQuestion) VALUES ('"
                        + s + "', '"
                        + 3 + "');");
            }
        }
        cursor.close();
    }
}
