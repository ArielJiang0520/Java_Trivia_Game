package com.example.company.lab5_ics45j;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameOver extends AppCompatActivity {
    int currentScore = 0;
    private static final String TAG = "GameOver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        Intent intent = getIntent();
        String curretnScore = intent.getStringExtra("currentScore");
        String end = intent.getStringExtra("end");
        TextView report = (TextView) findViewById( R.id.data);
        report.setText(end);
        currentScore = Integer.parseInt(curretnScore);
        //Set Current Score
        TextView cScore = (TextView) findViewById( R.id.score);
        cScore.setText(curretnScore);

        final Button homeButton = findViewById(R.id.buttonHome);
        homeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(GameOver.this, MainActivity.class));
            }
        });

        //Read highest score
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("userHighScore").child("userName");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String highestScore = dataSnapshot.getValue(String.class);
                    TextView scoreText = (TextView) findViewById( R.id.highestScore);
                    scoreText.setText(highestScore);
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
}
