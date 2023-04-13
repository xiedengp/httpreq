package com.example.myapp.uiview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PwdView extends AppCompatEditText {
    private final Paint paintLine, circlePaint, textPaint;

    private boolean isShowPwd = false;

    private char[] chars;

    public PwdView(@NonNull Context context) {
        this(context, null);
    }

    public PwdView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PwdView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setDither(true);
        paintLine.setColor(Color.parseColor("#DD2081"));
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(dip2px(1));

        circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.parseColor("#000000"));
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        circlePaint.setStrokeWidth(1);

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(dip2px(15));
        textPaint.setColor(Color.parseColor("#333333"));

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                chars = s.toString().toCharArray();
                if (s.length() != 0)
                    invalidate();
            }
        });
    }

    public void setIsShowPwd() {
        isShowPwd = !isShowPwd;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        MeasureSpec.getMode(widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        int w = getWidth() / 6;
        for (int i = 1; i < 6; i++) {
            canvas.drawLine(w * i, 0, w * i, getHeight(), paintLine);
        }
        int mid = w / 2;
        int length = getText().toString().length();
        if (isShowPwd) {

            Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
            for (int i = 0; i < length; i++) {
                Rect rect = new Rect();
                textPaint.getTextBounds(String.valueOf(chars[i]), 0, String.valueOf(chars[i]).length(), rect);
                int x = (mid - rect.width() / 2) + w * i;
                int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
                int baseLine = getHeight() / 2 + dy;
                canvas.drawText(String.valueOf(chars[i]), x, baseLine, textPaint);
            }

        } else {
            for (int i = 0; i < length; i++) { //写了几个字符就画几个实心圆！
                canvas.drawCircle(w * i + mid, getHeight() / 2, getHeight() / 14 + 10, circlePaint);
            }
        }


    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
