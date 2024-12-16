package com.example.studysudoku2;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private class Cell {
        private int value;
        private boolean fixed;
        private android.widget.Button button;

        public Cell(int intvalue, Context context) {
            value = intvalue;
            fixed = value != 0;

            button = new android.widget.Button(context);
            if (fixed) {
                button.setText(String.valueOf(value));
                button.setEnabled(false);
                button.setBackgroundColor(Color.WHITE);
                button.setTextColor(Color.rgb(78, 89, 104));
            } else {
                button.setText("");
                button.setBackgroundColor(Color.WHITE);
                button.setTextColor(Color.rgb(27, 100, 218));
                button.setStateListAnimator(null);
            }

            button.setOnClickListener(view -> {
                if (fixed) return;
                if (selectedCell != null && selectedCell != this) {
                    selectedCell.button.setBackgroundColor(Color.WHITE);
                }
                selectedCell = this;
                button.setBackgroundColor(Color.rgb(192, 217, 254));
            });
        }

        public void setValue(int newValue) {
            value = newValue;
            button.setText(value == 0 ? "" : String.valueOf(value));
        }
    }

    private Toolbar headerLayout;
    private Cell selectedCell = null;
    private Cell[][] table;
    private String input;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private long timeElapsed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Main Layout
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);

        // Header Layout
        headerLayout = createHeader("Sudoku Game");
        LinearLayout.LayoutParams headerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        headerLayout.setLayoutParams(headerParams);

        // Game Board Layout
        LinearLayout.LayoutParams boardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        boardParams.weight = 9;
        LinearLayout boardLayout = createGameBoard();
        boardLayout.setLayoutParams(boardParams);

        // Number Pad Layout
        GridLayout numberPadLayout = createNumberPad();
        LinearLayout.LayoutParams padParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        padParams.weight = 8;
        numberPadLayout.setLayoutParams(padParams);

        // Timer TextView
        timerTextView = new TextView(this);
        timerTextView.setText("00:00");
        timerTextView.setTextColor(Color.BLACK);
        timerTextView.setTextSize(24);

        LinearLayout.LayoutParams timerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        timerTextView.setLayoutParams(timerParams);

        // Add Views to Main Layout
        mainLayout.addView(headerLayout);
        mainLayout.addView(boardLayout);
        mainLayout.addView(numberPadLayout);
        mainLayout.addView(timerTextView);

        setContentView(mainLayout);

        // Start Timer
        startTimer();
    }

    private Toolbar createHeader(String title) {
        Toolbar toolbar = new Toolbar(this);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(Color.rgb(49, 130, 246));
        return toolbar;
    }

    private LinearLayout createGameBoard() {
        LinearLayout gameBoardLayout = new LinearLayout(this);

        // Game Board Initialization
        GridLayout gridBoard = new GridLayout(this);
        gridBoard.setColumnCount(9);
        gridBoard.setRowCount(9);

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int cellSize = screenWidth / 9;

        input = "3 8 ? 7 5 4 2 1 9 " +
                "7 ? 4 5 1 2 6 9 3 " +
                "2 1 6 3 9 8 7 5 4 " +
                "5 7 3 4 8 9 1 2 6 " +
                "9 4 1 2 ? 6 5 ? 8 " +
                "? 6 2 1 5 3 9 4 7 " +
                "? ? 8 9 2 5 4 7 1 " +
                "1 5 9 8 4 7 ? 6 2 " +
                "4 2 7 6 3 1 8 9 5 ";

        String[] split = input.split(" ");
        table = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String s = split[i * 9 + j];
                char c = s.charAt(0);

                table[i][j] = new Cell(c == '?' ? 0 : c - '0', this);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = cellSize;
                params.height = cellSize;
                params.rowSpec = GridLayout.spec(i);
                params.columnSpec = GridLayout.spec(j);

                gridBoard.addView(table[i][j].button, params);
            }
        }

        gameBoardLayout.addView(gridBoard);
        return gameBoardLayout;
    }

    private GridLayout createNumberPad() {
        GridLayout numberPad = new GridLayout(this);
        numberPad.setColumnCount(4);
        numberPad.setRowCount(3);

        int buttonSize = getResources().getDisplayMetrics().widthPixels / 4;

        for (int i = 1; i <= 9; i++) {
            final int number = i;
            android.widget.Button numberButton = new android.widget.Button(this);
            numberButton.setText(String.valueOf(number));
            numberButton.setBackgroundColor(Color.rgb(252, 252, 252));

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = buttonSize;
            params.height = buttonSize;
            params.rowSpec = GridLayout.spec((i - 1) / 3);
            params.columnSpec = GridLayout.spec((i - 1) % 3);

            numberButton.setLayoutParams(params);
            numberButton.setOnClickListener(v -> {
                if (selectedCell != null) {
                    selectedCell.setValue(number);
                }
            });

            numberPad.addView(numberButton);
        }

        android.widget.Button clearButton = new android.widget.Button(this);
        clearButton.setText("X");
        clearButton.setBackgroundColor(Color.rgb(252, 252, 252));

        GridLayout.LayoutParams deleteParams = new GridLayout.LayoutParams();
        deleteParams.width = buttonSize;
        deleteParams.height = buttonSize;

        deleteParams.rowSpec = GridLayout.spec(0);
        deleteParams.columnSpec = GridLayout.spec(3);
        clearButton.setLayoutParams(deleteParams);

        clearButton.setOnClickListener(v -> {
            if (selectedCell != null) {
                selectedCell.setValue(0);
            }
        });

        numberPad.addView(clearButton);

        return numberPad;
    }

    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeElapsed++;
                updateTimer();
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Time's up! Game over.", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) (timeElapsed / 60);
        int seconds = (int) (timeElapsed % 60);
        timerTextView.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
