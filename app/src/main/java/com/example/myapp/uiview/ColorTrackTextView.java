package com.example.myapp.uiview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.example.myapp.R;

/**
 * 可以整合viewPage  监听滑动距离 设置滑动Tab样式
 */
public class ColorTrackTextView extends androidx.appcompat.widget.AppCompatTextView {
    private int changeColor = Color.RED;
    private int originColor = Color.BLACK;
    private Paint changePaint;
    private Paint originPaint;

    private float mSeek = 0.5f;

    private Direction direction = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT, TOP_TO_BOTTOM, BOTTOM_TO_TOP
    }

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        changeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, changeColor);
        originColor = typedArray.getColor(R.styleable.ColorTrackTextView_originColor, originColor);
        changePaint = initPaint(changeColor);
        originPaint = initPaint(originColor);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        int mid = (int) (mSeek * getWidth());
        if (direction == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, 0, mid, changePaint);
            drawText(canvas, mid, getWidth(), originPaint);
        } else if (direction == Direction.RIGHT_TO_LEFT) {
            drawText(canvas, getWidth() - mid, getWidth(), changePaint);
            drawText(canvas, 0, getWidth() - mid, originPaint);
        } else {
            int topMid = (int) (mSeek * getHeight());
            if (direction == Direction.TOP_TO_BOTTOM) {
                drawTextTop(canvas, 0, topMid, changePaint);
                drawTextTop(canvas, topMid, getHeight(), originPaint);
            } else if (direction == Direction.BOTTOM_TO_TOP) {
                drawTextTop(canvas, getHeight() - topMid, getHeight(), changePaint);
                drawTextTop(canvas, 0, getHeight() - topMid, originPaint);
            }
        }

    }

    private void drawText(Canvas canvas, int start, int end, Paint paint) {
        canvas.save();

        Rect rectText = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rectText); //裁剪区域

        String text = getText().toString();

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int dx = getWidth() / 2 - rect.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseLine, paint);

        canvas.restore();
    }


    private void drawTextTop(Canvas canvas, int start, int end, Paint paint) {
        canvas.save();

        Rect rectText = new Rect(0, start, getWidth(), end);
        canvas.clipRect(rectText); //裁剪区域

        String text = getText().toString();

        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        int dx = getWidth() / 2 - rect.width() / 2;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(text, dx, baseLine, paint);

        canvas.restore();
    }

    private Paint initPaint(int color) {

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true); //防抖动
        paint.setColor(color);
        paint.setTextSize(getTextSize());

        return paint;
    }

    public void setmSeek(float seek) {
        mSeek = seek;
        invalidate();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
