package com.example.myapp.uiview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import com.example.myapp.R;

public class ProgressBar extends View {
    private int innerBackground = Color.BLACK;
    private int outBackground = Color.RED;
    private int rounderWidth = 8;
    private int textSize = 15;
    private int textColor = Color.RED;

    private Paint innerPaint, outPaint, textPaint;
    private int max = 100, currentValue = 0;

    public void setMax(int max) {
        if (max == 0) return;
        this.max = max;
    }

    public void setCurrentValue(int currentValue) {

        ValueAnimator animator = ObjectAnimator.ofFloat(0f,currentValue);
        animator.setDuration(2000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            int value = (int)(Float.parseFloat(animation.getAnimatedValue().toString()));
            this.currentValue = value;
            invalidate();

        });
        animator.start();
//        invalidate();

    }

    public ProgressBar(Context context) {
        this(context, null);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        innerBackground = typedArray.getColor(R.styleable.ProgressBar_innerBackground, innerBackground);
        outBackground = typedArray.getColor(R.styleable.ProgressBar_outBackground, outBackground);
        textColor = typedArray.getColor(R.styleable.ProgressBar_progressTextColor, textColor);
        rounderWidth = typedArray.getDimensionPixelSize(R.styleable.ProgressBar_rounderWidth, dip2px(rounderWidth));
        textSize = typedArray.getDimensionPixelSize(R.styleable.ProgressBar_progressTextSize, sp2px(textSize));

        innerPaint = new Paint();
        innerPaint.setAntiAlias(true);
        innerPaint.setStrokeWidth(rounderWidth);
        innerPaint.setColor(innerBackground);
        innerPaint.setStyle(Paint.Style.STROKE);
        innerPaint.setDither(true);

        outPaint = new Paint();
        outPaint.setAntiAlias(true);
        outPaint.setStrokeWidth(rounderWidth);
        outPaint.setColor(outBackground);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setDither(true);

        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);

        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //保证正方形
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

        //圆
        int center = getWidth() / 2;
        canvas.drawCircle(center, center, center - rounderWidth / 2, innerPaint);


        //外圆
        if (currentValue == 0) return;
        RectF rectF = new RectF(rounderWidth /2, rounderWidth / 2, getWidth() - rounderWidth / 2, getHeight() - rounderWidth / 2);
        float sweepAngle = (float) currentValue / max;
        canvas.drawArc(rectF, 0, sweepAngle * 360, false, outPaint);


        //文字
        String str = (int)(sweepAngle * 100) + "%";
        Rect rect = new Rect();
        textPaint.getTextBounds(str, 0, str.length(), rect);
        int x = getWidth() / 2 - rect.width() / 2;

        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(str, x, baseLine, textPaint);
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());

    }
}
