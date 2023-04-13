package com.example.myapp.loadingpoint;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

public class LoadingPointView extends RelativeLayout {
    private final CircleView leftView;
    private final CircleView rightView;
    private final CircleView midView;

    private boolean isAnimatorStop = false; //动画是否停止

    private int animatorMoveDistance = 20;
    private int circleSize = 10;

    public LoadingPointView(Context context) {
        this(context, null);
    }

    public LoadingPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        animatorMoveDistance = dip2px(animatorMoveDistance);
        circleSize = dip2px(circleSize);
        setBackgroundColor(Color.WHITE);
        leftView = getCircleView(context);
        leftView.setPaintColor(Color.RED);
        rightView = getCircleView(context);
        rightView.setPaintColor(Color.BLUE);
        midView = getCircleView(context);
        midView.setPaintColor(Color.YELLOW);
        addView(leftView);
        addView(rightView);
        addView(midView);
        post(this::expandAnimator);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawColor(Color.RED);
    }

    private CircleView getCircleView(Context context) {
        CircleView circleView = new CircleView(context);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(circleSize, circleSize);
        layoutParams.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(layoutParams);
        return circleView;
    }

    private void expandAnimator() {
        if(isAnimatorStop) return;
        System.out.println("Animator");
        ObjectAnimator leftTranslation = ObjectAnimator.ofFloat(leftView, "translationX", 0, -animatorMoveDistance);
        ObjectAnimator rightTranslation = ObjectAnimator.ofFloat(rightView, "translationX", 0, animatorMoveDistance);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(leftTranslation, rightTranslation);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.setDuration(350);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                innerAnimator();
            }
        });
        animatorSet.start();
    }

    private void innerAnimator() {
        if(isAnimatorStop) return;
        System.out.println("Animator");
        ObjectAnimator leftTranslation = ObjectAnimator.ofFloat(leftView, "translationX", -animatorMoveDistance, 0);
        ObjectAnimator rightTranslation = ObjectAnimator.ofFloat(rightView, "translationX", animatorMoveDistance, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.playTogether(leftTranslation, rightTranslation);
        animatorSet.setDuration(350);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                // 左边拿中间  中间拿右边 右边拿左边
                int leftColor = leftView.getPaintColor();
                int rightColor = rightView.getPaintColor();
                int midColor = midView.getPaintColor();

                leftView.setPaintColor(midColor);
                midView.setPaintColor(rightColor);
                rightView.setPaintColor(leftColor);

                expandAnimator();
            }
        });
        animatorSet.start();
    }

    @Override
    public void setVisibility(int visibility) {
        if(visibility==GONE|| visibility==INVISIBLE){
            super.setVisibility(INVISIBLE);
            isAnimatorStop = true;
        }
        //清理动画
        leftView.clearAnimation();
        rightView.clearAnimation();

        //从父布局移除
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this); //从父布局移除
            removeAllViews(); //移除自己的view
        }
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }
}
