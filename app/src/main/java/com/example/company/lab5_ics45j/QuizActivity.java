package com.example.company.lab5_ics45j;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static java.lang.Thread.*;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";

    int score = 0;
    int lives = 3;
    String currentQuestionNum;
    //Need an answer variable that updates depending on question

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        String newQuestion = getRandomQuestion();
        currentQuestionNum = newQuestion;
        getQuestion(newQuestion);


        final Button button1 = findViewById(R.id.choice1);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("choice1");//Temporary for testing purposes, should call checkAnswer() instead
            }
        });

        final Button button2 = findViewById(R.id.choice2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("choice2");
            }
        });

        final Button button3 = findViewById(R.id.choice3);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                checkAnswer("choice3");//Temporary for testing purposes, should call checkAnswer() instead
            }
        });

    }

    private void checkAnswer(final String userChoice)
    {

        //Use the choice to determine if the answer was correct or not
        //Depending whether it is correct or not, update the score or the life
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("questions");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    //String v = dataSnapshot.child(key).child("question").getValue(String.class);

                    Log.d(TAG, "This is before change(Global): " + currentQuestionNum);
                    DataSnapshot questionObj = dataSnapshot.child(currentQuestionNum);
                    String answer = questionObj.child("answer").getValue(String.class);
                    Log.d(TAG, "This is before change(Answer): " + answer);
                    Log.d(TAG, "This is before change(User's choice): " + userChoice);
                    if(userChoice.compareTo(answer) == 0)
                    {
                        int resID = getResources().getIdentifier(userChoice,
                                "id", getPackageName());
                        Button rightAnswerView = (Button) findViewById( resID );
                        rightAnswerView.setBackgroundColor(Color.parseColor("#ff00ddff"));
                        //try{
                        //    sleep(1000);
                        //}
                        //catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                         //   e.printStackTrace();
                        //}
                        rightAnswerView.setBackgroundColor(Color.parseColor("#ffffffff"));
                        //Log.d(TAG, rightAnswerView.getText());
                        //Correct
                        addScore();
                    }else{
                        //Incorrect
                        subtractLife();
                    }

                    String newQuestion = getRandomQuestion();
                    currentQuestionNum = newQuestion;
                    Log.d(TAG, "This is after change: " + currentQuestionNum);
                    getQuestion(newQuestion);



                    /*if ( answer != null)
                        Log.d(TAG, answer);
                    else
                        Log.d(TAG, "value is NULL");*/

                }
                else
                    Log.d(TAG, "snapshot does not exist.");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.");
            }
        });
    }

    private String getRandomQuestion()
    {
        //Get a random number from 1 - 30
        Random rand = new Random();
        int n = rand.nextInt(10);
        n+=1;

        return "question" + n;
    }

    //Get Question from database and choices from database
    private void getQuestion(final String key)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("questions");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    //String v = dataSnapshot.child(key).child("question").getValue(String.class);

                    DataSnapshot questionObj = dataSnapshot.child(key);
                    String v = questionObj.child("question").getValue(String.class);
                    //Log.d(TAG, key);
                    String c1 = questionObj.child("choice1").getValue(String.class);
                    String c2 = questionObj.child("choice2").getValue(String.class);
                    String c3 = questionObj.child("choice3").getValue(String.class);
                    //System.out.println(c1);

                    //Set question text
                    TextView question = (TextView) findViewById( R.id.question);
                    question.setText( v );

                    //Set buttons text
                    setQuestionText(c1, c2, c3);

                    /*if ( c3!= null)
                        Log.d(TAG, c3);
                    else
                        Log.d(TAG, "value is NULL");*/

                }
                else
                    Log.d(TAG, "snapshot does not exist.");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.");
            }
        });
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
