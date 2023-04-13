package com.example.myapp.goodstyle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.myapp.R;

import java.util.Random;

//点赞的效果动画
public class LoveImage extends RelativeLayout {
    private int viewHeight;
    private int viewWidth;
    private final int[] imgArr = {R.mipmap.love1, R.mipmap.love2, R.mipmap.love3};
    private final Random random = new Random();
    private  final Drawable drawable;

    public LoveImage(Context context) {
        this(context, null);
    }

    public LoveImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public LoveImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        drawable = context.getDrawable(R.mipmap.love1);
        initLayout();

    }

    public void initLayout() {
        ImageView imageView = new ImageView(getContext());
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_HORIZONTAL);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(imgArr[random.nextInt(imgArr.length )]);
        addView(imageView);

        post(() -> startAnimator(imageView));
    }

    private void startAnimator(View view) {
        AnimatorSet allAnimator = new AnimatorSet();
        PointF point0;
        PointF point1;
        PointF point2;
        PointF point3;
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(350);
        animatorSet.playTogether(scaleX, scaleY);

        point0 = new PointF(viewWidth / 2f - drawable.getIntrinsicWidth() / 2f, viewHeight - drawable.getIntrinsicHeight());
        point1 = new PointF(random.nextInt(viewWidth) - drawable.getIntrinsicWidth() / 2f, random.nextInt(viewHeight / 2));
        point2 = new PointF(random.nextInt(viewWidth) - drawable.getIntrinsicWidth() / 2f, random.nextInt(viewHeight / 2) + viewHeight / 2f);
        point3 = new PointF(random.nextInt(viewWidth) - drawable.getIntrinsicWidth() / 2f, 0);
        LoveTypeEvaluator evaluator = new LoveTypeEvaluator(point1, point2);
        ValueAnimator valueAnimator = ObjectAnimator.ofObject(evaluator, point0, point3);
        valueAnimator.addUpdateListener(animation -> {
            PointF pointF = (PointF) animation.getAnimatedValue();
            view.setX(pointF.x);
            view.setY(pointF.y);
            view.setAlpha(1 - animation.getAnimatedFraction() + 0.2f);
        });
        valueAnimator.setDuration(2000);
        allAnimator.playSequentially(animatorSet, valueAnimator);
        allAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                removeView(view);
            }
        });

        allAnimator.start();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);

    }
}
