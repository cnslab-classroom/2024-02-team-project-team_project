package com.example.studysudoku2;

import android.os.Handler;
import android.widget.TextView;

import java.util.Locale;

public class Timer {
    private int timeInSeconds = 0; // 타이머 시간 (초 단위)
    private final Handler handler = new Handler();
    private final TextView timerTextView; // 타이머를 표시할 TextView
    private boolean isRunning = false; // 타이머 상태

    public Timer(TextView timerTextView) {
        this.timerTextView = timerTextView;
    }

    // 타이머 시작
    public void start() {
        isRunning = true;
        handler.postDelayed(timerRunnable, 1000);
    }

    // 타이머 정지
    public void stop() {
        isRunning = false;
        handler.removeCallbacks(timerRunnable);
    }

    // 타이머 초기화
    public void reset() {
        stop();
        timeInSeconds = 0;
        updateTimerUI();
    }

    // 타이머 업데이트 Runnable
    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                timeInSeconds++;
                updateTimerUI();
                handler.postDelayed(this, 1000);
            }
        }
    };

    // TextView에 시간 업데이트
    private void updateTimerUI() {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timeFormatted);
    }

    public int getElapsedTimeInSeconds() {
        return timeInSeconds;
    }
}
