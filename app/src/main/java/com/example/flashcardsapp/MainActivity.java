package com.example.flashcardsapp;

import static android.graphics.Color.YELLOW;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer;
    TextView tvAnswer1;
    TextView tvAnswer2;
    TextView tvAnswer3;
    ImageView toggleChoiceVisibility;
    ImageView toggleChoiceInvisibility;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout parentId = findViewById(R.id.patentId);
        flashcardQuestion = findViewById(R.id.flashcard_question);
        flashcardAnswer = findViewById(R.id.flashcard_answer);
        tvAnswer1 = findViewById(R.id.tvAnswer1);
        tvAnswer2 = findViewById(R.id.tvAnswer2);
        tvAnswer3 = findViewById(R.id.tvAnswer3);
        toggleChoiceVisibility = findViewById(R.id.toggle_choice_visibility);
        toggleChoiceInvisibility = findViewById(R.id.toggle_choice_invisibility);


        flashcardQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });
        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);
            }
        });

        tvAnswer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAnswer1.setBackgroundColor(Color.parseColor("#f44336"));
            }
        });
        tvAnswer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAnswer2.setBackgroundColor(Color.parseColor("#f44336"));
            }
        });
        tvAnswer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAnswer3.setBackgroundColor(Color.parseColor("#118f11"));

            }
        });
        parentId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAnswer1.setBackgroundColor(Color.parseColor("#f6b26b"));
                tvAnswer2.setBackgroundColor(Color.parseColor("#f6b26b"));
                tvAnswer3.setBackgroundColor(Color.parseColor("#f6b26b"));
            }
        });

        toggleChoiceVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAnswer1.setVisibility(View.INVISIBLE);
                tvAnswer2.setVisibility(View.INVISIBLE);
                tvAnswer3.setVisibility(View.INVISIBLE);
                toggleChoiceVisibility.setVisibility(View.INVISIBLE);
                toggleChoiceInvisibility.setVisibility(View.VISIBLE);
            }
        });

        toggleChoiceInvisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAnswer1.setVisibility(View.VISIBLE);
                tvAnswer2.setVisibility(View.VISIBLE);
                tvAnswer3.setVisibility(View.VISIBLE);
                toggleChoiceVisibility.setVisibility(View.VISIBLE);
                toggleChoiceInvisibility.setVisibility(View.INVISIBLE);
            }
        });

        ImageView addButton = findViewById(R.id.ivAddIcon);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            // get data
            if (data != null){
                String questionString = data.getExtras().getString("QUESTION_KEY");
                String answerString = data.getExtras().getString("ANSWER_KEY");
                flashcardQuestion.setText(questionString);
                flashcardAnswer.setText(answerString);
                tvAnswer1.setVisibility(View.INVISIBLE);
                tvAnswer2.setVisibility(View.INVISIBLE);
                tvAnswer3.setVisibility(View.INVISIBLE);
                Snackbar.make(findViewById(R.id.flashcard_question),
                        "New flash card created",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }
}