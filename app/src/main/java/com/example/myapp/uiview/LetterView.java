package com.example.myapp.uiview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class LetterView extends View {
    private final String[] strings = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U"
            , "V", "W", "X", "Y", "Z","#"};
    private final Paint textPaint, srcPaint;
    private final int textSize = 15;
    private final int colorText = Color.BLACK;
    private final int colorTextOpt = Color.BLUE;

    private int textMaxWidth, textMaxHeight, size; //单个文字最长宽度和高度
    private final int textMid = 8; //文字之间的距离

    private int viewHeight;

    private int current = -1; //选中的位置


    public LetterView(Context context) {
        this(context, null);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setDither(true);
        textPaint.setColor(colorText);
        textPaint.setTextSize(dip2px(textSize));
        srcPaint = new Paint();
        srcPaint.setColor(Color.YELLOW);
        srcPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Rect rect = new Rect();
        for (int i = 0; i < strings.length; i++) {
            textPaint.getTextBounds(strings[i], 0, strings[i].length(), rect);
            if (rect.width() > textMaxWidth)
                textMaxWidth = rect.width();
            if (rect.width() > textMaxHeight)
                textMaxHeight = rect.height();

        }
        size = Math.max(textMaxHeight, textMaxWidth);

        setMeasuredDimension(size, LinearLayout.LayoutParams.MATCH_PARENT);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.bottom;


        for (int i = 0; i < strings.length; i++) {

            int baseLine = (size + dip2px(textMid)) * i + dy + (viewHeight - size * strings.length - dip2px(textMid) * (strings.length - 1)) / 2;
            Rect rect = new Rect();
            textPaint.getTextBounds(strings[i], 0, strings[i].length(), rect);
            int x = (size - rect.width()) / 2;

            if (i == current) {
                canvas.drawCircle(size / 2f, baseLine - rect.height() / 2f, size / 2f, srcPaint);
            }
            canvas.drawText(strings[i], x, baseLine, textPaint);

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();

                int topViewY = (viewHeight - (size + dip2px(textMid)) * strings.length) / 2;
                int bottomViewY = viewHeight - topViewY;
                if (moveY > topViewY && moveY < bottomViewY) {

                    int item = (int) (moveY - topViewY) / (size + dip2px(textMid));
                    if (current != item) {
                        current = item;
                        if(null!=checkMoveKeyListener)
                            checkMoveKeyListener.moveKey(strings[current],true);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(null!=checkMoveKeyListener)
                    checkMoveKeyListener.moveKey("",false);
                break;
        }
        return true;
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    private CheckMoveKeyListener checkMoveKeyListener = null;
    public void setCheckMoveKeyListener(CheckMoveKeyListener checkMoveKeyListener){
        this.checkMoveKeyListener = checkMoveKeyListener;
    }
    public interface CheckMoveKeyListener{
        void moveKey(String s,boolean isShow);
    }

}
