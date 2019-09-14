package com.glowedupgiftinc.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    Boolean counterIsActive = false;
    Button button;
    CountDownTimer countDownTimer;

    public void resetTimer(){
        textView.setText("0:30s");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        button.setText("GO");
        counterIsActive = false;
    }


    public void buttonClicked(View view){

        if (counterIsActive){
            resetTimer();

        } else {
            counterIsActive = true;
            seekBar.setEnabled(false);
            button.setText("STOP");
            countDownTimer = new CountDownTimer((seekBar.getProgress() * 1000) + 100, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int) (l / 1000));
                }

                @Override
                public void onFinish() {
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    mplayer.start();
                    resetTimer();
                }
            }.start();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        button = findViewById(R.id.button);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void updateTimer(int secondsLeft){
        int min = secondsLeft/60;
        int seconds = secondsLeft - (min * 60);
        String secondsString = Integer.toString(seconds);

        if (seconds <= 9){
            secondsString = "0" + secondsString;
        }

        textView.setText(Integer.toString(min) + ":" + secondsString);
    }
}
