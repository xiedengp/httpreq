package com.example.myapp.uiview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapp.R;


public class MySeekBar extends View {
    private Paint inPaint, outPaint;
    private int inColor = Color.GRAY;
    private int outColor = Color.BLUE;
    private int lineWithe = 10;

    private Paint srcPaint;

    private int currentValue = 0;
    private String currentText = "%0";

    public MySeekBar(Context context) {
        this(context, null);
    }

    public MySeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressBar);
        initPaint(context);
    }

    private void initPaint(Context context) {
        inPaint = new Paint();
        inPaint.setColor(inColor);
        inPaint.setAntiAlias(true);
        inPaint.setStrokeWidth(dp2px(context, lineWithe));
        inPaint.setStyle(Paint.Style.STROKE);
        inPaint.setStrokeCap(Paint.Cap.ROUND);

        outPaint = new Paint();
        outPaint.setColor(outColor);
        outPaint.setStrokeWidth(dp2px(context, lineWithe));
        outPaint.setAntiAlias(true);
        outPaint.setStyle(Paint.Style.STROKE);
        outPaint.setStrokeCap(Paint.Cap.ROUND);

        srcPaint = new Paint();
        srcPaint.setAntiAlias(true);
        srcPaint.setColor(Color.YELLOW);
    }

    ViewGroup.MarginLayoutParams lp;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec); layout_marginLeft
        lp = (ViewGroup.MarginLayoutParams) getLayoutParams();
        int margin = lp.leftMargin + lp.rightMargin;
        int width = MeasureSpec.getSize(widthMeasureSpec) - margin;
        int height = dp2px(getContext(), lineWithe);
        setMeasuredDimension(width, height * 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        canvas.drawLine(lp.leftMargin, getMeasuredHeight() / 2, getMeasuredWidth() + lp.leftMargin, getMeasuredHeight() / 2, inPaint);
        if (currentValue != 0) {
            canvas.drawLine(lp.leftMargin, getMeasuredHeight() / 2, currentValue + lp.leftMargin, getMeasuredHeight() / 2, outPaint);
            canvas.drawCircle(currentValue + lp.leftMargin, getMeasuredHeight() / 2, getMeasuredHeight() / 2, srcPaint);
        }

    }

    public void setSeekNum() {
        int width = getMeasuredWidth();
        ValueAnimator animator = ObjectAnimator.ofFloat(0, 100);
        animator.setDuration(3000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            int value = (int) (Float.parseFloat(animation.getAnimatedValue().toString()));
            this.currentValue = (int) (value / 100f * width);
            invalidate();
        });
        animator.start();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                if (moveX < getMeasuredWidth()) {
                    currentValue = (int) moveX;
                }
                break;
        }
        invalidate();
        return true;
    }

    public int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }
}
