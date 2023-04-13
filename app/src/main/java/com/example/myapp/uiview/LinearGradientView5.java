package com.example.myapp.uiview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LinearGradientView5 extends View {

    private final Paint paint;
    private RectF mRectF;

    public LinearGradientView5(Context context) {
        this(context,null);
    }

    public LinearGradientView5(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LinearGradientView5(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initRect(w,h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRectF,paint);
    }

    private void initRect(int width, int height) {
        mRectF = new RectF(0, 0, width, height);
        paint.setShader(new LinearGradient(0, 0, mRectF.right, 0,
                new int[]{Color.RED, Color.BLUE},
                new float[]{0f, 1.0f},
                Shader.TileMode.CLAMP));
    }

}
