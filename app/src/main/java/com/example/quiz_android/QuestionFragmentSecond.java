package com.example.quiz_android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;


public class QuestionFragmentSecond extends Fragment implements Gettable {
    private final int QUESTION_NUMBER = 2;
    private final int AMOUNT_ANSWERS = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_second, container, false);

        // Если хотим получить список ответов напрямую из xml:
        //String question = getResources().getString(R.string.question_2);
        //String[] answers = getResources().getStringArray(R.array.answers_2_decorations);

        // Получаем вопрос и ответы из БД через интерфейс Gettable:
        String question = getQuestionFromDb(getActivity(), view, QUESTION_NUMBER);
        String[] answers = getAnswersFromDb(getActivity(), view, QUESTION_NUMBER, AMOUNT_ANSWERS);

        TextView textView = view.findViewById(R.id.textViewQuestion2);
        textView.setText(question);

        Spinner spinner = view.findViewById(R.id.spinnerAnswers2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, answers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case (0):
                        AQActivity.setFlag(0);
                        putBundle(0);
                        break;
                    case (1):
                    case (2):
                        AQActivity.setFlag(1);
                        putBundle(0);
                        break;
                    case (3):
                        AQActivity.setFlag(1);
                        putBundle(1);
                        break;
                    default:
                        //
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //
            }
        });

        return view;
    }

    private void putBundle(int score) {
        Bundle result = new Bundle();
        result.putInt(AQActivity.SCORE_KEY, score);
        getParentFragmentManager().setFragmentResult(AQActivity.REQUEST_KEY_2, result);
    }
}