package com.example.myapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.ColorInt;

public class StatusBarUtil {
    /**
     * 状态栏设置颜色
     *
     * @param activity
     * @param color
     */
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4 - 5.0 之间  先设置为全屏 在加一个状态栏布局 设置颜色
            //  activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);  //这个状态栏文字不会在

            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            //加一个布局  高度是状态栏的高度
            View view = new View(activity);
            view.setBackgroundColor(color);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity)));
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
//            ViewGroup decorView = activity.findViewById(android.R.id.content);
            //android:fitsSystemWindows="true"
            decorView.addView(view);

            ViewGroup contentView = activity.findViewById(android.R.id.content); //拿到根布局
//            contentView.setPadding(0,getStatusBarHeight(activity),0,0);
            View activityView = contentView.getChildAt(0);
            activityView.setFitsSystemWindows(true);
//            activityView.setPadding(0,getStatusBarHeight(activity),0,0);

        }

    }

    private static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int statusBarHeightId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelOffset(statusBarHeightId);

    }

    /**
     * 设置activity 全屏
     *
     * @param activity
     */
    public static void steTranslucent(Activity activity) {
        //5.0以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            ActionBar actionBar = activity.getActionBar();
//            actionBar.hide();
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //4.4-5.0
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

    }
}
