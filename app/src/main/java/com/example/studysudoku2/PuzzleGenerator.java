package com.example.studysudoku2;

public class PuzzleGenerator {
    private int[][] board = new int[9][9];

    public int[][] generatePuzzle(String difficulty) {
        fillBoard();
        int emptyCells = getEmptyCellCount(difficulty);
        removeCells(emptyCells);
        return board;
    }

    private void fillBoard() {
        // 백트래킹으로 보드 완성
    }

    private int getEmptyCellCount(String difficulty) {
        switch (difficulty) {
            case "Easy": return 35;
            case "Medium": return 45;
            case "Hard": return 55;
            case "Expert": return 65;
            default: return 40;
        }
    }

    private void removeCells(int count) {
        // 무작위로 빈 칸 생성 (단, 유효성 확인)
    }
}
