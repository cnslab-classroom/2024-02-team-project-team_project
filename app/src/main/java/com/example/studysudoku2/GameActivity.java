package com.example.studysudoku2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private int elapsedTimeInSeconds;  // TimerActivity에서 전달받은 시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        scoreTextView = findViewById(R.id.scoreTextView);  // 게임 화면에 점수 표시할 TextView

        // TimerActivity에서 전달된 타이머 시간을 Intent로 받기
        Intent intent = getIntent();
        elapsedTimeInSeconds = intent.getIntExtra("TIMER_TIME", 0);

        // 게임 종료 시 점수 계산
        calculateAndDisplayScore();
    }

    // 타이머 시간을 기반으로 점수 계산
    private void calculateAndDisplayScore() {
        int score = calculateScore(elapsedTimeInSeconds);
        scoreTextView.setText("Your Score: " + score);
    }

    // 타이머 시간을 기반으로 점수 계산 (예시: 1초당 10점 감점)
    private int calculateScore(int elapsedTimeInSeconds) {
        int baseScore = 1000;  // 기본 점수
        int penalty = elapsedTimeInSeconds * 10;  // 초당 10점씩 감점
        return Math.max(0, baseScore - penalty);  // 감점이 0보다 낮으면 0을 반환
    }
}
