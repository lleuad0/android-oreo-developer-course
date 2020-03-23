package com.example.mrodionova.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    TextView timeLeft;
    TextView problem;
    MathProblem mathProblem;
    TextView check;
    AnswerCounter answerCounter;
    TextView answersGiven;
    boolean isGameActive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int timerDuration = 30010;
        int timerPeriod = 1000;

        timeLeft = findViewById(R.id.timeLeft);
        renewTimeLeft(timerDuration);
        countDownTimer = new CountDownTimer(timerDuration, timerPeriod) {
            @Override
            public void onTick(long millisUntilFinished) {
                renewTimeLeft(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                timeLeft.setText("done");
                check.setText("Well done!");
                findViewById(R.id.playAgainButton).setVisibility(View.VISIBLE);
                isGameActive = false;
                check.setAlpha(1f);
            }
        };

        problem = findViewById(R.id.problemTextView);
        check = findViewById(R.id.checkAnswerText);
        answersGiven = findViewById(R.id.answersTextView);
    }

    public void startGame(View view) {
        view.setVisibility(View.GONE);
        findViewById(R.id.gameField).setVisibility(View.VISIBLE);
        countDownTimer.start();
        isGameActive = true;
        countDownTimer.start();
        isGameActive = true;
        newProblem();
        answerCounter = new AnswerCounter();
        answersGiven.setText(answerCounter.message());
    }

    public void renewTimeLeft(long time) {
        String message;
        message = String.valueOf(time / 1000);
        timeLeft.setText(message);
    }

    public void setAnswers() {
        Button zero = findViewById(R.id.answer0);
        Button one = findViewById(R.id.answer1);
        Button two = findViewById(R.id.answer2);
        Button three = findViewById(R.id.answer3);

        int random = new Random().nextInt(4);
        switch (random) {
            case 0:
                zero.setText(mathProblem.getRightAnswer());
                zero.setTag(1);
                one.setText(mathProblem.getPotentialAnswer());
                one.setTag(null);
                two.setText(mathProblem.getPotentialAnswer());
                two.setTag(null);
                three.setText(mathProblem.getPotentialAnswer());
                three.setTag(null);
                break;

            case 1:
                one.setText(mathProblem.getRightAnswer());
                one.setTag(1);
                zero.setText(mathProblem.getPotentialAnswer());
                zero.setTag(null);
                two.setText(mathProblem.getPotentialAnswer());
                two.setTag(null);
                three.setText(mathProblem.getPotentialAnswer());
                three.setTag(null);
                break;

            case 2:
                two.setText(mathProblem.getRightAnswer());
                two.setTag(1);
                one.setText(mathProblem.getPotentialAnswer());
                one.setTag(null);
                zero.setText(mathProblem.getPotentialAnswer());
                zero.setTag(null);
                three.setText(mathProblem.getPotentialAnswer());
                three.setTag(null);
                break;

            case 3:
                three.setText(mathProblem.getRightAnswer());
                three.setTag(1);
                one.setText(mathProblem.getPotentialAnswer());
                one.setTag(null);
                two.setText(mathProblem.getPotentialAnswer());
                two.setTag(null);
                zero.setText(mathProblem.getPotentialAnswer());
                zero.setTag(null);
                break;

        }
    }

    public void checkAnswer(View view) {
        if (isGameActive) {
            check.setAlpha(1f);
            if (view.getTag() == null) {
                check.setText("Wrong");
                answerCounter.givenWrong();
                newProblem();
            } else {
                check.setText("Right");
                answerCounter.givenRight();
                newProblem();
            }
            check.animate().alpha(0f).setDuration(1000);
            answersGiven.setText(answerCounter.message());
        }
    }

    public void newProblem() {
        mathProblem = new MathProblem();
        problem.setText(mathProblem.message);
        setAnswers();
    }

    public void playAgain(View view) {
        answerCounter = new AnswerCounter();
        newProblem();
        check.setAlpha(0f);
        view.setVisibility(View.GONE);
        countDownTimer.start();
        isGameActive = true;
    }
}
