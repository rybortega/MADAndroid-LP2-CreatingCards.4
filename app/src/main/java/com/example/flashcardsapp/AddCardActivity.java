package com.example.flashcardsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ImageView ivCancel = findViewById(R.id.ivCancel);
        ImageView ivSave = findViewById(R.id.ivSave);

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
                String inputQuestion = ((EditText) findViewById(R.id.etInputQuestion)).getText().toString();
                String inputAnswer = ((EditText) findViewById(R.id.etInputAnswer)).getText().toString();
                data.putExtra("QUESTION_KEY", inputQuestion);
                data.putExtra("ANSWER_KEY", inputAnswer);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
}