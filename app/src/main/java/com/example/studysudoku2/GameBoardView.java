package com.example.studysudoku2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import android.util.Log;

public class GameBoardView extends View{
    private Paint linePaint;

    public GameBoardView(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public GameBoardView(Context context){
        super(context, null);
        init();
    }

    private void init(){
        linePaint = new Paint();
        linePaint.setColor(Color.rgb(242,242,242)); //선 색상
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(4); //선 두께
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        Log.d("GameBoardView", "onDraw called");


        int width = getWidth();
        int height = getHeight();

        //셀 크기
        int cellSize = width / 9;

        //가로선
        for (int i = 1; i < 9; i++){
            canvas.drawLine(0, i * cellSize, width, i * cellSize, linePaint);
        }
        //세로선
        for (int i = 1; i < 9; i++){
            canvas.drawLine(i * cellSize, 0, i * cellSize, height, linePaint);
        }
        //외곽선
        canvas.drawRect(0, 0, width, height, linePaint);
    }
}
