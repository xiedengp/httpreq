package com.example.myapp.uiview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.myapp.screen.MenuAdapter;

public class ListDataScreenView extends LinearLayoutCompat implements View.OnClickListener{
    private LinearLayoutCompat tabView; //顶部菜单
    private final Context mContext;

    private FrameLayout menuFrameLayout; //内容布局

    private View view; //阴影

    private int contentHeight = 0;

    private MenuAdapter adapter;

    private int selectPosition = -1;

    private boolean isStartAnimator = false; //是否在执行动画中

    public ListDataScreenView(@NonNull Context context) {
        this(context, null);
    }

    public ListDataScreenView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListDataScreenView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (contentHeight == 0) {
            System.out.println("openContentMenu ->onMeasure");
            contentHeight = (int) (MeasureSpec.getSize(heightMeasureSpec) * 0.75);
            ViewGroup.LayoutParams layoutParams = menuFrameLayout.getLayoutParams();
            layoutParams.height = contentHeight;
            menuFrameLayout.setBackgroundColor(Color.YELLOW);
            menuFrameLayout.setLayoutParams(layoutParams);
            menuFrameLayout.setTranslationY(-contentHeight);
        }
    }

    private void initLayout() {

        //初始化布局 可以加载xml  也可以代码实现
        setOrientation(LinearLayoutCompat.VERTICAL);

        tabView = new LinearLayoutCompat(mContext);
        tabView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(tabView);

        FrameLayout frameLayout = new FrameLayout(mContext);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(frameLayout);

        view = new View(mContext);
        view.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setBackgroundColor(Color.parseColor("#80999999"));
        view.setAlpha(0f);
        view.setVisibility(GONE);
        frameLayout.addView(view);

        view.setOnClickListener(this);

        //创建菜单 存放内容
        menuFrameLayout = new FrameLayout(mContext);
        menuFrameLayout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.addView(menuFrameLayout);
    }



    public void setAdapter(MenuAdapter adapter) {
        if (adapter == null) return;
        this.adapter = adapter;
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            View view1 = adapter.getChildView(i, tabView);
            LinearLayoutCompat.LayoutParams params = (LayoutParams) view1.getLayoutParams();
            params.width = 0;
            params.weight = 1;
            view1.setLayoutParams(params);
            tabView.addView(view1);
            setOnclickTabView(view1, i);

            //todo 内容布局 实际需求中 可以分开写adapter模式  适配不同的样式
            View contentView = adapter.getContentView(i, menuFrameLayout);
            contentView.setVisibility(GONE);
            menuFrameLayout.addView(contentView);
        }
    }

    private void setOnclickTabView(View tabChildView, int position) {
        tabChildView.setOnClickListener(v -> {
            //动画执行的中的时候 就不要处理点击事件了
            if (isStartAnimator) return;
            //去显示和关闭内容布局
            if (selectPosition == -1) {
                openContentMenu(tabChildView, position);
            } else {
                if (selectPosition == position)
                    closeContentMenu();
                else {
                    adapter.closeTab(tabView.getChildAt(selectPosition));
                    menuFrameLayout.getChildAt(selectPosition).setVisibility(INVISIBLE);
                    selectPosition = position;
                    menuFrameLayout.getChildAt(selectPosition).setVisibility(VISIBLE);
                    adapter.openTab(tabView.getChildAt(selectPosition));
                }
            }

        });
    }

    private void closeContentMenu() {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(menuFrameLayout, "translationY", 0, -contentHeight);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(350);
        animatorSet.playTogether(translationY, alpha);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isStartAnimator = false;
                view.setVisibility(GONE);
                selectPosition = -1;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isStartAnimator = true;
                menuFrameLayout.getChildAt(selectPosition).setVisibility(INVISIBLE);
                adapter.closeTab(tabView.getChildAt(selectPosition));
            }
        });
        animatorSet.start();
    }

    private void openContentMenu(View tabChildView, int position) {
        System.out.println("openContentMenu");
        menuFrameLayout.getChildAt(position).setVisibility(VISIBLE);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(menuFrameLayout, "translationY", -contentHeight, 0);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        view.setVisibility(VISIBLE);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(350);
        animatorSet.playTogether(translationY, alpha);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isStartAnimator = false;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                isStartAnimator = true;
                selectPosition = position;
                adapter.openTab(tabChildView);
            }
        });
        animatorSet.start();
    }

    @Override
    public void onClick(View v) {
        closeContentMenu();
    }
}
