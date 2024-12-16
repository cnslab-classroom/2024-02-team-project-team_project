package com.example.studysudoku2;

public class ScoreCalculator {

    // 점수 계산 메서드
    public int calculateScore(int elapsedTimeInSeconds) {
        int baseScore = 100; // 지역 변수로 사용
        return baseScore - elapsedTimeInSeconds; // finalScore 제거
    }
}