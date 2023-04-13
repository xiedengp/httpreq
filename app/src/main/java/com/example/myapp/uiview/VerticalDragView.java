package com.example.myapp.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class VerticalDragView extends FrameLayout {
    private ViewDragHelper viewDragHelper;
    private View mDragListView;
    private int mMenuHeight; //后面菜单的高度
    boolean mIsOpen; //是否打开

    public VerticalDragView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, callback);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mMenuHeight = getChildAt(0).getMeasuredHeight();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int count = getChildCount();
        if (count != 2) {
            throw new RuntimeException("只能有两个子布局！！");
        }
        mDragListView = getChildAt(1);
    }

    @Override
    public void computeScroll() {
        //响应滚动
        if (viewDragHelper.continueSettling(true))
            invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    //事件处理和拦截
    private float mDownY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        //打开的时候要全部拦截
        if (mIsOpen) {
            return true;
        }

        //父view拦截子view
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                //viewDragHelper 拿一个完整的事件
                viewDragHelper.processTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                //拦截向下滑动  而且是滚动到了顶部
                System.out.println("canChildScrollUp ->" + mDragListView.canScrollVertically(-1));
                if (ev.getY() - mDownY > 0 && !mDragListView.canScrollVertically(-1))
                    return true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                viewDragHelper.shouldInterceptTouchEvent(ev);
                mDownY = 0;
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }


    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            //是否可以拖动

            return mDragListView == child; //只能拖动前面
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            //垂直拖动移动的位置
            //不能超出后面view的高度
            if (top < 0)
                top = 0;
            if (top >= mMenuHeight)
                top = mMenuHeight;
            return top;
        }


//        @Override
//        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//            //水平拖动移动的位置
//            return left;
//        }


        //手指松开的时候
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            System.out.println("yvel -> " + yvel);
            if (releasedChild == mDragListView) {
                //松开的时候 打开还是关闭
                if (mDragListView.getTop() > mMenuHeight / 2) {
                    //打开 滚动到菜单高度
                    viewDragHelper.settleCapturedViewAt(0, mMenuHeight);
                    mIsOpen = true;
                } else {
                    //关闭 滚动到0
                    viewDragHelper.settleCapturedViewAt(0, 0);
                    mIsOpen = false;
                }
                invalidate();
            }

        }


    };
}
