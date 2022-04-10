package com.example.flashcardsapp;

import static android.graphics.Color.YELLOW;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer;
    TextView tvAnswer1;
    TextView tvAnswer2;
    TextView tvAnswer3;
    ImageView toggleChoiceVisibility;
    ImageView toggleChoiceInvisibility;
    ImageView ivEdit;
    ImageView ivNext;
    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;
    int randomCard;

    // returns a random number between minNumber and maxNumber, inclusive.
    // for example, if i called getRandomNumber(1, 3), there's an equal chance of it returning either 1, 2, or 3.
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }
    CountDownTimer countDownTimer;

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
        ivEdit = findViewById(R.id.ivEdit);
        ivNext = findViewById(R.id.ivNext);
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());

        allFlashcards = flashcardDatabase.getAllCards();



        countDownTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.timer)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
            }
        };

        if (allFlashcards != null && allFlashcards.size() > 0) {
            flashcardQuestion.setText(allFlashcards.get(0).getQuestion());
            flashcardAnswer.setText(allFlashcards.get(0).getAnswer());
            tvAnswer3.setText(allFlashcards.get(0).getAnswer());

        }
        startTimer();

        flashcardQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // get the center for the clipping circle
                int cx = flashcardAnswer.getWidth() / 2;
                int cy = flashcardAnswer.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(flashcardAnswer, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);

                anim.setDuration(1000);
                anim.start();

                //flashcardQuestion.setVisibility(View.INVISIBLE);
                //flashcardAnswer.setVisibility(View.VISIBLE);
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
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                String questionString = flashcardQuestion.getText().toString();
                String answerString = flashcardAnswer.getText().toString();

                intent.putExtra("stringKey1", questionString);
                intent.putExtra("stringKey2", answerString);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer();

                flashcardAnswer.setVisibility(View.INVISIBLE);

                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);


                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                        flashcardQuestion.startAnimation(rightInAnim);

                        // Moved previous logic here


                        // don't try to go to next card if you have no cards to begin with
                        if (allFlashcards.size() == 0)
                            return;
                        //tvAnswer1.setVisibility(View.INVISIBLE);
                        //tvAnswer2.setVisibility(View.INVISIBLE);
                        //tvAnswer3.setVisibility(View.INVISIBLE);

                        // advance our pointer index so we can show the next card
                        currentCardDisplayedIndex++;

                        // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list

                        if(currentCardDisplayedIndex >= allFlashcards.size()) {
                            Snackbar.make(flashcardQuestion,
                                    "You've reached the end of the cards!!!!",
                                    Snackbar.LENGTH_SHORT)
                                    .show();
                            currentCardDisplayedIndex = 0;
                        }

                        // set the question and answer TextViews with data from the database

                        randomCard = getRandomNumber(0, allFlashcards.size()-1);
                        while (currentCardDisplayedIndex == randomCard && allFlashcards.size() > 1){
                            randomCard = getRandomNumber(0, allFlashcards.size()-1);
                        }
                        flashcardQuestion.setText(allFlashcards.get(randomCard).getQuestion());
                        flashcardQuestion.setVisibility(View.VISIBLE);
                        flashcardAnswer.setText(allFlashcards.get(randomCard).getAnswer());
                        tvAnswer3.setText(allFlashcards.get(randomCard).getAnswer());

                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                flashcardQuestion.startAnimation(leftOutAnim);

            }

        });

        findViewById(R.id.ivDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardDatabase.deleteCard(flashcardQuestion.getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayedIndex--;

                if(allFlashcards.size() == 0) {
                    Snackbar.make(flashcardQuestion,
                            "No saved flashcard!",
                            Snackbar.LENGTH_SHORT)
                            .show();

                    flashcardQuestion.setText("Add a new card!!!!!");

                    tvAnswer1.setVisibility(View.INVISIBLE);
                    tvAnswer2.setVisibility(View.INVISIBLE);
                    tvAnswer3.setVisibility(View.INVISIBLE);
                    return;
                }
                // set the question and answer TextViews with data from the database
                randomCard = getRandomNumber(0, allFlashcards.size()-1);
                while (currentCardDisplayedIndex == randomCard && currentCardDisplayedIndex != 0 ){
                    randomCard = getRandomNumber(0, allFlashcards.size()-1);
                }
                flashcardQuestion.setText(allFlashcards.get(randomCard).getQuestion());
                flashcardAnswer.setText(allFlashcards.get(randomCard).getAnswer());
                tvAnswer3.setText(allFlashcards.get(randomCard).getAnswer());
            }
        });
    }

    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
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
                Snackbar.make(flashcardQuestion,
                        "Card successfully created",
                        Snackbar.LENGTH_SHORT)
                        .show();
                flashcardDatabase.insertCard(new Flashcard(questionString, answerString));
                allFlashcards = flashcardDatabase.getAllCards();
            }
        }
    }
}