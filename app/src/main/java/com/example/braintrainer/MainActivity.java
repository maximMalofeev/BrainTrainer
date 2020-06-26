package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button goButton;
    ConstraintLayout gameLayout;
    TextView taskTextView;
    TextView scoreTextView;
    TextView timerTextView;
    TextView gameOverTextView;
    ArrayList<Button> buttons;
    final int optionsCount = 4;
    ArrayList<Integer> options;
    int rightAnswerIndex;
    int rightCount = 0;
    int attempts = 0;
    final int timeToPlay = 30;
    CountDownTimer timer;

    public void makeNewQuestion(){
        options.clear();

        Random random = new Random();
        int a = random.nextInt(21);
        int b = random.nextInt(21);
        taskTextView.setText(String.format("%d + %d", a, b));

        rightAnswerIndex = random.nextInt(4);

        for(int i = 0; i < optionsCount; i++){
            if(i == rightAnswerIndex){
                options.add(a + b);
            }else{
                options.add(random.nextInt(41));
            }
            buttons.get(i).setText(options.get(i).toString());
        }
    }

    public void onGo(View view){
        goButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        gameOverTextView.setVisibility(View.INVISIBLE);
        makeNewQuestion();
        scoreTextView.setText(String.format("%d/%d", 0, 0));
        timerTextView.setText(String.format("%ds", timeToPlay));
        timer.start();
    }

    public void onAnswer(View view){
        attempts++;
        if(rightAnswerIndex == Integer.valueOf(view.getTag().toString())){
            rightCount++;
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Wrong", Toast.LENGTH_SHORT).show();
        }
        scoreTextView.setText(String.format("%d/%d", rightCount, attempts));
        makeNewQuestion();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button)findViewById(R.id.goButton);
        gameLayout = (ConstraintLayout)findViewById(R.id.gameLayout);
        taskTextView = (TextView)findViewById(R.id.taskTextView);
        scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        timerTextView = (TextView)findViewById(R.id.timerTextView);
        gameOverTextView = (TextView)findViewById(R.id.gameOverTextView);
        options = new ArrayList<Integer>();
        buttons = new ArrayList<Button>();
        buttons.add((Button)findViewById(R.id.button0));
        buttons.add((Button)findViewById(R.id.button1));
        buttons.add((Button)findViewById(R.id.button2));
        buttons.add((Button)findViewById(R.id.button3));
        timer = new CountDownTimer(timeToPlay * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.format("%ds", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                gameLayout.setVisibility(View.INVISIBLE);
                gameOverTextView.setText("Try Again?");
                gameOverTextView.setVisibility(View.VISIBLE);
                goButton.setVisibility(View.VISIBLE);
            }
        };
    }
}