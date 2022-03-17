package com.example.flashcardsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    EditText etInputQuestion;
    EditText etInputAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ImageView ivCancel = findViewById(R.id.ivCancel);
        ImageView ivSave = findViewById(R.id.ivSave);
        etInputQuestion = findViewById(R.id.etInputQuestion);
        etInputAnswer = findViewById(R.id.etInputAnswer);

        String s1 = getIntent().getStringExtra("stringKey1");
        String s2 = getIntent().getStringExtra("stringKey2");

        etInputQuestion.setText(s1);
        etInputAnswer.setText(s2);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                String inputQuestion = etInputQuestion.getText().toString();
                String inputAnswer = etInputAnswer.getText().toString();
                if (inputQuestion.isEmpty()||inputAnswer.isEmpty()){
                    Toast.makeText(AddCardActivity.this, "Must enter both question and answer", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(), "Must enter both question and answer!", Toast.LENGTH_SHORT).show();
                }
                else{
                    data.putExtra("QUESTION_KEY", inputQuestion);
                    data.putExtra("ANSWER_KEY", inputAnswer);
                    setResult(RESULT_OK, data);
                    finish();
                }

            }
        });

    }
}