package com.example.myapp.loadingpoint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleView extends View {
    private final Paint paint;
    private int mPaintColor;
    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width,height),Math.min(width,height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int dx = getWidth()/2;
        canvas.drawCircle(dx,dx,dx,paint);
    }


    public void setPaintColor(int color){
        mPaintColor = color;
        paint.setColor(color);
        invalidate();
    }

    public int getPaintColor(){
        return mPaintColor;
    }
}
