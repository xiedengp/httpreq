package com.example.myapp.loadingpoint;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;



public class LoadExpendView extends View {
    private static final long ANIMATOR_TIME = 2000;
    //控件的宽高
    private int viewHeight, viewWidth;

    private final Paint paint;
    private final int[] colorArr = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.BLACK, Color.CYAN};
    private int expendRadius = 10;

    private final float angleIndex;


    public LoadExpendView(Context context) {
        this(context, null);
    }

    public LoadExpendView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadExpendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        angleIndex = (float) ((2 * Math.PI) / 6);
        expendRadius = dp2sp(expendRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    LoadState loadState = null;

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (loadState == null)
            loadState = new RotateState();
        loadState.draw(canvas);


    }

    private int dp2sp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public static abstract class LoadState {
        public abstract void draw(Canvas canvas);

    }

    public void loadEndAnimator() {
        if (loadState != null && loadState instanceof RotateState)
            ((RotateState) loadState).cancel();
        loadState = new ScaleState();
//        ServiceLoader

    }

    //旋转动画
    public class RotateState extends LoadState {
        private final ValueAnimator animator;
        float changeAngle = 0;

        public RotateState() {

            animator = ObjectAnimator.ofFloat(0f, (float) (2 * Math.PI));
            animator.setDuration(ANIMATOR_TIME);
            animator.setInterpolator(new LinearInterpolator());
            animator.setRepeatCount(-1);
            animator.addUpdateListener(animation -> {
                changeAngle = (float) animation.getAnimatedValue();
                invalidate();
            });
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            for (int i = 0; i < colorArr.length; i++) {
                paint.setColor(colorArr[i]);
                float angle = i * angleIndex + changeAngle;
                float cx = (float) (viewWidth / 4 * Math.cos(angle));
                float cy = (float) (viewWidth / 4 * Math.sin(angle));
                canvas.drawCircle(viewWidth / 2f + cx, viewHeight / 2f + cy, expendRadius, paint);
            }
        }

        public void cancel() {
            animator.cancel();
        }
    }

    //缩放动画
    public class ScaleState extends LoadState {
        private float outRadius = viewWidth / 4f;

        public ScaleState() {
            ValueAnimator animator = ObjectAnimator.ofFloat(outRadius, 0f);
            animator.setDuration(ANIMATOR_TIME/2);
            animator.addUpdateListener(animation -> {
                outRadius = (float) animation.getAnimatedValue();

                invalidate();
            });
            animator.setInterpolator(new AnticipateOvershootInterpolator(3f));
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    loadState = new ExpendState();
                }
            });
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            for (int i = 0; i < colorArr.length; i++) {
                paint.setColor(colorArr[i]);
                float angle = i * angleIndex;
                float cx = (float) (outRadius * Math.cos(angle));
                float cy = (float) (outRadius * Math.sin(angle));
                canvas.drawCircle(viewWidth / 2f + cx, viewHeight / 2f + cy, expendRadius, paint);
            }
        }
    }

    //扩散动画
    public class ExpendState extends LoadState {

        private final float diagonalRadius;
        private float currentRadius = 0;

        public ExpendState() {
            //获取对角线一半的长度
            diagonalRadius = (float) Math.sqrt(viewWidth / 2f * viewWidth / 2f + viewHeight / 2f * viewHeight / 2f);
            ValueAnimator animator = ObjectAnimator.ofFloat(0f, diagonalRadius);
            animator.setDuration(ANIMATOR_TIME/2);
            animator.addUpdateListener(animation -> {
                currentRadius = (float) animation.getAnimatedValue();
                invalidate();
            });
            animator.setInterpolator(new AccelerateInterpolator());
            animator.start();
        }

        @Override
        public void draw(Canvas canvas) {
            float cx = viewWidth / 2f;
            float cy = viewHeight / 2f;
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(diagonalRadius - currentRadius);
            paint.setStyle(Paint.Style.STROKE);
            float radius = (diagonalRadius - currentRadius) / 2 + currentRadius;
            canvas.drawCircle(cx, cy, radius, paint);

        }
    }


}
