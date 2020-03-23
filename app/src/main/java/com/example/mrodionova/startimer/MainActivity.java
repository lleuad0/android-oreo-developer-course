package com.example.mrodionova.startimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


class TimeRenewer {
    int renewable;

    TimeRenewer(int time) {
        renewable = time / 1000;
    }

    String renew() {
        String message;
        int min = renewable / 60;
        int sec = renewable - min * 60 + 1;
        if (sec == 60) {
            sec = 0;
            min += 1;
        }
        message = String.valueOf(min) + ":";
        if (sec < 10) {
            message += "0";
        }
        message += String.valueOf(sec);
        return message;
    }
}

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;
    int durationMs;
    int periodMs;
    TextView timeView;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    boolean isTimerActive = false;
    TimeRenewer timeRenewer;
    Button timeButton;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        durationMs = 65000;
        periodMs = 1000;
        timeView = findViewById(R.id.timeLeftTextView);
        seekBar = findViewById(R.id.timeSeekBar);
        seekBar.setMax(300);
        seekBar.setProgress(durationMs / 1000);
        timeButton = findViewById(R.id.goButton);

        countDownTimer = makeTimer(durationMs, periodMs);
        isTimerActive = false;
        activeTimer(timeButton);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    durationMs = progress * 1000;
                    countDownTimer = makeTimer(durationMs, periodMs);
                    timeRenewer = new TimeRenewer(durationMs);
                    timeView.setText(timeRenewer.renew());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (isTimerActive) {
                    countDownTimer.cancel();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress() * 1000;
                countDownTimer = makeTimer(progress, periodMs);
                timeRenewer = new TimeRenewer(progress);
                timeView.setText(timeRenewer.renew());
            }
        });

    }

    public void activeTimer(View view) {
        Button timeButton = (Button) view;
        if (isTimerActive) {
            isTimerActive = false;
            countDownTimer.cancel();
            timeButton.setText("Go");
            seekBar.setEnabled(true);
        } else {
            isTimerActive = true;
            countDownTimer = makeTimer(seekBar.getProgress() * 1000, periodMs);
            countDownTimer.start();
            timeButton.setText("Stop");
            seekBar.setEnabled(false);
        }

    }

    public CountDownTimer makeTimer(int duration, int period) {

        return new CountDownTimer(duration, period) {
            @Override
            public void onTick(long millisUntilFinished) {
                int millsInt = (int) millisUntilFinished;
                timeRenewer = new TimeRenewer(millsInt);
                timeView.setText(timeRenewer.renew());
                seekBar.setProgress(millsInt / 1000);
            }

            @Override
            public void onFinish() {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.oth);
                mediaPlayer.start();
                isTimerActive = false;
                timeButton.setText("finished");
                timeButton.setEnabled(false);
                timeView.setText("0:00");
            }
        };
    }

}