package com.example.myapp.uiview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ShapeView extends View {
    public Shape currentShape = Shape.Square;
    private final Paint paint;
    private Path path;

    public enum Shape {
        Circle, Triangle, Square
    }

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
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
        super.onDraw(canvas);
        switch (currentShape) {
            case Circle:
                paint.setColor(Color.RED);
                int center = getWidth() / 2;
                canvas.drawCircle(center, center, center, paint);
                break;
            case Square:
                paint.setColor(Color.BLUE);
                RectF rectF = new RectF(0, 0, getWidth(), getHeight());
                canvas.drawRect(rectF, paint);
                break;
            case Triangle:
                paint.setColor(Color.YELLOW);
                if (path == null) {
                    path = new Path();
                    path.moveTo(getWidth() / 2, 0);
                    path.lineTo(0, (float) (getWidth() / 2 * Math.sqrt(3)));
                    path.lineTo(getWidth(), (float) (getWidth() / 2 * Math.sqrt(3)));
                    path.close();
                }
                canvas.drawPath(path, paint);

                break;
        }
    }

    public void changeShape() {
        switch (currentShape) {
            case Circle:
                currentShape = Shape.Triangle;
                break;
            case Triangle:
                currentShape = Shape.Square;
                break;
            case Square:
                currentShape = Shape.Circle;
                break;
        }
        invalidate();
    }
}
