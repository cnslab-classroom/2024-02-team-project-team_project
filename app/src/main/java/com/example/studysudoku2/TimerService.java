package com.example.studysudoku2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import androidx.annotation.Nullable;

public class TimerService extends Service {

    private final IBinder binder = new TimerBinder();
    private Handler handler = new Handler();
    private int seconds = 0;
    private boolean isRunning = false;

    public class TimerBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void startTimer() {
        if (!isRunning) {
            isRunning = true;
            handler.post(timerRunnable);
        }
    }

    public void pauseTimer() {
        isRunning = false;
        handler.removeCallbacks(timerRunnable);
    }

    public void resetTimer() {
        pauseTimer();
        seconds = 0;
    }

    public int getSeconds() {
        return seconds;
    }

    // 새로운 메서드: 현재 타이머 시간을 초 단위로 반환
    public int getTimeInSeconds() {
        return seconds;
    }

    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (isRunning) {
                seconds++;
                handler.postDelayed(this, 1000); // 1초마다 실행
            }
        }
    };
}
