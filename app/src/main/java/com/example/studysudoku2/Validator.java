package com.example.studysudoku2;

//MakeBoard.java를 Android 환경에서 동작하도록 수정
public class Validator{
    //스도쿠 보드 저장하는 2차원 배열
    private int[][] board;

    public Validator(int[][] board){
        this.board = board;
    }

    //특정 좌표에 숫자 n이 유효한지 확인
    public boolean isPossible(int x, int y, int n){
        // Row and Column Check
        for (int i = 0; i < 9; i++) {
            if (board[x][i] == n || board[i][y] == n){
                return false;
            }
        }

        int startRow = (x / 3) * 3;
        int startCol = (y / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++){
            for (int j = startCol; j < startCol + 3; j++){
                if (board[i][j] == n) {
                    return false;
                }
            }
        }
        return true;
    }

    //특정 좌표의 값을 설정
    public void updateBoard(int x, int y, int value){
        board[x][y] = value;
    }

    //보드 반환
    public int[][] getBoard(){
        return board;
    }
}
