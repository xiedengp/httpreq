package com.example.myapp.uiview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.myapp.R;

public class LoadingView extends LinearLayoutCompat {
    private ShapeView shapeView;
    private View shadowView;
    private final int mTranslationDp;
    private final long timeAnimator = 350L; //动画执行的时间

    //是否停止动画
    private boolean isStopAnimator = false;

    public LoadingView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDp = dip2px(80);
        initLayout();
    }

    private void initLayout() {
        //加载布局到 this parent
        /**
         *    public static View inflate(Context context, @LayoutRes int resource, ViewGroup root) {
         *         LayoutInflater factory = LayoutInflater.from(context);
         *         return factory.inflate(resource, root);
         *     }
         */
        inflate(getContext(), R.layout.ui_loading, this);
        shapeView = findViewById(R.id.shape_view);
        shadowView = findViewById(R.id.view);

        post(this::startFallAnimator);

    }

    //下落动画
    private void startFallAnimator() {

        if (isStopAnimator) return;
        //下落
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(shapeView, "translationY", 0, mTranslationDp);
        //缩放动画
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(shadowView, "scaleX", 1f, 0.3f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnimator, scaleAnimator); //同时执行
        animatorSet.setDuration(timeAnimator);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                shapeView.changeShape();
                startUpAnimator();

            }


        });
        animatorSet.start();

    }

    //上移动画
    private void startUpAnimator() {
        if (isStopAnimator) return;
        System.out.println("startUpAnimator->");
        //上移
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(shapeView, "translationY", mTranslationDp, 0);
        //缩放 放大
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(shadowView, "scaleX", 0.3f, 1f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnimator, scaleAnimator); //同时执行
        animatorSet.setDuration(timeAnimator);
        animatorSet.setInterpolator(new DecelerateInterpolator());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                startFallAnimator();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                startRotationAnimator();
            }


        });
        animatorSet.start();
    }

    //开始旋转
    ObjectAnimator rotationAnimator;
    private void startRotationAnimator() {
        //三角60  其他180
        switch (shapeView.currentShape) {
            case Square:
            case Circle:
                rotationAnimator = ObjectAnimator.ofFloat(shapeView, "rotation", 0, 180);
                break;
            case Triangle:
                rotationAnimator = ObjectAnimator.ofFloat(shapeView, "rotation", 0, 120);
                break;
        }
        rotationAnimator.setDuration(timeAnimator);
        rotationAnimator.setInterpolator(new DecelerateInterpolator());
        rotationAnimator.start();
    }

    //性能优化  隐藏的时候 动画其实还在走
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(View.INVISIBLE); // 优化 不要再去摆放和计算  少走系统的源码
        isStopAnimator = true;
        //清理动画
        shapeView.clearAnimation();
        shadowView.clearAnimation();

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
