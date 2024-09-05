package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private Button buttonStart, buttonPause, buttonReset;

    private Handler handler;
    private long startTime, timeInMillis, timeSwapBuff, updateTime = 0L;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTimer = findViewById(R.id.textViewTimer);
        buttonStart = findViewById(R.id.buttonStart);
        buttonPause = findViewById(R.id.buttonPause);
        buttonReset = findViewById(R.id.buttonReset);

        handler = new Handler();

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void startTimer() {
        if (!isRunning) {
            startTime = System.currentTimeMillis();
            handler.postDelayed(updateTimerThread, 0);
            isRunning = true;
        }
    }

    private void pauseTimer() {
        if (isRunning) {
            timeSwapBuff += timeInMillis;
            handler.removeCallbacks(updateTimerThread);
            isRunning = false;
        }
    }

    private void resetTimer() {
        startTime = 0L;
        timeInMillis = 0L;
        timeSwapBuff = 0L;
        updateTime = 0L;
        isRunning = false;

        handler.removeCallbacks(updateTimerThread);
        textViewTimer.setText("00:00:000");
    }

    private final Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMillis = System.currentTimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMillis;

            int secs = (int) (updateTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updateTime % 1000);

            textViewTimer.setText(String.format("%02d:%02d:%03d", mins, secs, milliseconds));

            handler.postDelayed(this, 0);
        }
    };
}
