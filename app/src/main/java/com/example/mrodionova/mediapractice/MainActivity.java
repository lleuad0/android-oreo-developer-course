package com.example.mrodionova.mediapractice;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void applyImage(View view) {
        findViewById(R.id.initialTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.frameImage).setVisibility(View.VISIBLE);
        findViewById(R.id.frameVideo).setVisibility(View.GONE);
        findViewById(R.id.frameAudio).setVisibility(View.GONE);

        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }

        Toast.makeText(this, "Image mode", Toast.LENGTH_SHORT).show();
        imageAnimation(findViewById(R.id.imageView));
    }

    public void applyVideo(View view) {
        findViewById(R.id.initialTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.frameImage).setVisibility(View.GONE);
        findViewById(R.id.frameVideo).setVisibility(View.VISIBLE);
        findViewById(R.id.frameAudio).setVisibility(View.GONE);

        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }

        Toast.makeText(this, "Video mode", Toast.LENGTH_SHORT).show();
        videoPlayer(findViewById(R.id.videoView));
    }

    public void applyAudio(View view) {
        findViewById(R.id.initialTextView).setVisibility(View.INVISIBLE);
        findViewById(R.id.frameImage).setVisibility(View.GONE);
        findViewById(R.id.frameVideo).setVisibility(View.GONE);
        findViewById(R.id.frameAudio).setVisibility(View.VISIBLE);

        Toast.makeText(this, "Audio mode", Toast.LENGTH_SHORT).show();
        audioTrack();
    }

    public void imageAnimation(View view) {
        view.setTranslationY(-500);
        view.setAlpha(0);
        view.animate().translationYBy(500).alphaBy(1).rotationBy(360).setDuration(2000);
    }

    public void videoPlayer(View view) {
        VideoView videoView = findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.tga486);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(view);
        videoView.setMediaController(mediaController);

        videoView.start();

    }

    public void audioTrack() {
        mediaPlayer = MediaPlayer.create(this, R.raw.oth);
        mediaPlayer.start();

        seekBarLogic(findViewById(R.id.seekBar));
    }

    protected void pause(View view) {
        try {
            mediaPlayer.pause();
        } catch (Exception e) {
        }
    }

    public void play(View view) {
        try {
            mediaPlayer.start();
        } catch (Exception e) {
        }
    }

    public void seekBarLogic(View view) {
        final SeekBar seekBar = (SeekBar) view;
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                int position = mediaPlayer.getCurrentPosition();
                seekBar.setProgress(position);
            }
        }, 0, 1500);
    }

}
