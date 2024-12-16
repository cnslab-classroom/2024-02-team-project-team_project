package com.example.studysudoku2;
import java.util.ArrayList;
import java.util.List;
public class HintProvider {
    private int[][] board;
    private boolean[][] fixedCells;

    public HintProvider(int[][] board, boolean[][] fixedCells) {
        this.board = board;
        this.fixedCells = fixedCells;
    }

    public String getHint() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    int[] possibleValues = getPossibleValues(i, j);
                    if (possibleValues.length == 1) {
                        return "Cell (" + (i+1) + ", " + (j+1) + ") should be " + possibleValues[0];
                    }
                }
            }
        }
        return "No simple hints available!";
    }

    private int[] getPossibleValues(int row, int col) {
        boolean[] used = new boolean[10]; // 1~9 사용 여부
        for (int i = 0; i < 9; i++) {
            if (board[row][i] != 0) used[board[row][i]] = true;
            if (board[i][col] != 0) used[board[i][col]] = true;
        }
        int boxStartRow = (row / 3) * 3;
        int boxStartCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[boxStartRow + i][boxStartCol + j] != 0) {
                    used[board[boxStartRow + i][boxStartCol + j]] = true;
                }
            }
        }
        return getUnusedValues(used);
    }

    private int[] getUnusedValues(boolean[] used) {
        List<Integer> unusedValues = new ArrayList<>();
        for (int i = 1; i <= 9; i++) { // 1부터 9까지 확인
            if (!used[i]) {
                unusedValues.add(i);
            }
        }
        return unusedValues.stream().mapToInt(i -> i).toArray(); // 리스트를 배열로 변환
    }
}

