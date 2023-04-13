package com.example.myapp.uiview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapp.R;

public class QQStepView extends View {
    private int mOutColor = Color.RED;
    private int mInColor = Color.BLUE;
    private int mTextColor = Color.BLACK;
    private int mTextSize = 15;
    private int mBorderWidth = 5;

    private Paint mOutPaint;

    private int mStepMax = 100;
    private int mStepCurrent = 50;
    private Paint mInPaint;

    private Paint mTextPaint;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);

        mOutColor = array.getColor(R.styleable.QQStepView_outColor, mOutColor);
        mInColor = array.getColor(R.styleable.QQStepView_inColor, mInColor);
        mTextColor = array.getColor(R.styleable.QQStepView_qqTextColor, mTextColor);
        mTextSize = array.getDimensionPixelSize(R.styleable.QQStepView_qqTextSize, mTextSize);
        mBorderWidth = array.getDimensionPixelSize(R.styleable.QQStepView_bordeWidth, mBorderWidth);

        mOutPaint = new Paint();
        mOutPaint.setAntiAlias(true);
        mOutPaint.setColor(mOutColor);
        mOutPaint.setStrokeWidth(mBorderWidth);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeCap(Paint.Cap.ROUND);

        mInPaint = new Paint();
        mInPaint.setAntiAlias(true);
        mInPaint.setColor(mInColor);
        mInPaint.setStrokeWidth(mBorderWidth);
        mInPaint.setStyle(Paint.Style.STROKE);
        mInPaint.setStrokeCap(Paint.Cap.ROUND);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(spTopx(context, mTextSize));
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heigh = MeasureSpec.getSize(heightMeasureSpec);


        int newWidth = Math.min(width, heigh);
        setMeasuredDimension(newWidth, newWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mBorderWidth / 2;
        //外圆弧
        RectF rectF = new RectF(center - radius, center - radius, center + radius, center + radius);
        canvas.drawArc(rectF, 135, 270, false, mOutPaint);

        //内圆弧
        if (mStepMax == 0) return;
        float sweepAngle = (float) mStepCurrent / mStepMax;
        canvas.drawArc(rectF, 135, sweepAngle * 270, false, mInPaint);

        //画文字
        Rect rect = new Rect();

        String step = mStepCurrent + "";
        //文字开始x
        mTextPaint.getTextBounds(step, 0, step.length(), rect);
        int dx = getWidth() / 2 - rect.width() / 2;

        //文字基线
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
        int baseLine = getWidth() / 2 + dy;
        canvas.drawText(step, dx, baseLine, mTextPaint);

    }

    public void setStepMax(int mStepMax) {
        this.mStepMax = mStepMax;
    }

    public void setStepCurrent(int stepCurrent) {

        this.mStepCurrent = stepCurrent;
        invalidate();
    }

    public static int spTopx(Context context, int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }


}
