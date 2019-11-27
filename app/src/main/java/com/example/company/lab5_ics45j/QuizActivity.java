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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        final Button button1 = findViewById(R.id.choice1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView scoreView = (TextView) findViewById( R.id.textView2);
                score += 1;
                scoreView.setText( Integer.toString(score));
            }
        });

        final Button button2 = findViewById(R.id.choice2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(lives == 3)
                {
                    ImageView life3 = (ImageView) findViewById( R.id.life2);
                    life3.setVisibility(View.INVISIBLE);
                }
            }
        });

        final Button button3 = findViewById(R.id.choice3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView scoreView = (TextView) findViewById( R.id.textView2);
                score *= 5;
                scoreView.setText( Integer.toString(score));
            }
        });
    }
}
