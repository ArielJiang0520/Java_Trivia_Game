package com.example.company.lab5_ics45j;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class QuizActivity extends AppCompatActivity {

    int score = 0;
    int lives = 3;
    //Need an answer variable that updates depending on question

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final Button button1 = findViewById(R.id.choice1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addScore();//Temporary for testing purposes, should call checkAnswer() instead
            }
        });

        final Button button2 = findViewById(R.id.choice2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                subtractLife();
            }
        });

        final Button button3 = findViewById(R.id.choice3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addScore();//Temporary for testing purposes, should call checkAnswer() instead
            }
        });
    }

    private void checkAnswer(int choice)
    {
        //Use the choice to determine if the answer was correct or not
        //Depending whether it is correct or not, update the score or the life
    }

    private void subtractLife()
    {
        if(lives == 3)
        {
            ImageView life3 = (ImageView) findViewById( R.id.life2);
            life3.setVisibility(View.INVISIBLE);
            lives--;
        }else if (lives == 2)
        {
            ImageView life2 = (ImageView) findViewById( R.id.life1);
            life2.setVisibility(View.INVISIBLE);
            lives--;
        }else if (lives == 1)
        {
            ImageView life1 = (ImageView) findViewById( R.id.life0);
            life1.setVisibility(View.INVISIBLE);
            lives--;
        }else{
            //TODO, End the game
        }
    }

    //Add one point to the current score
    private void addScore()
    {
        TextView scoreView = (TextView) findViewById( R.id.textView2);
        score += 1;
        scoreView.setText( Integer.toString(score));
    }

    //Set Question Text
    private void setQuestionText(String newQuestion)
    {
        TextView question = (TextView) findViewById( R.id.question);
        question.setText(newQuestion);
    }

    //Set choices Text
    private void setQuestionText(String q1, String q2, String q3)
    {
        TextView choice1 = (TextView) findViewById( R.id.choice1);
        TextView choice2 = (TextView) findViewById( R.id.choice2);
        TextView choice3 = (TextView) findViewById( R.id.choice3);
        choice1.setText(q1);
        choice2.setText(q2);
        choice3.setText(q3);
    }
}
