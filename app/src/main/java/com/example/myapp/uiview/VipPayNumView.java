package com.example.myapp.uiview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

public class VipPayNumView extends LinearLayoutCompat {
    public VipPayNumView(@NonNull Context context) {
        super(context,null);
    }

    public VipPayNumView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public VipPayNumView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayoutCompat.HORIZONTAL);
        setBackgroundColor(Color.RED);
        setGravity(Gravity.END);

//        initLayout(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initLayout(getContext());
    }

    private void initLayout(Context context) {
        TextView tv1 = new TextView(context);
        tv1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tv1.setTextSize(dp2px(10));
        TextView tv2 = new TextView(context);
        tv2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        tv2.setTextSize(dp2px(30));
        tv1.setText("¥");
        tv2.setText("30");
        tv1.setTextColor(Color.BLUE);
        tv2.setTextColor(Color.BLUE);

        Rect rect = new Rect();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            tv2.getPaint().getTextBounds(tv2.getText(), 0, tv2.getText().length(), rect);
        }
        tv2.getPaint().setShader(new LinearGradient(0, 0, rect.right, 0,
                new int[]{Color.RED, Color.BLUE},
                new float[]{0f, 1.0f},
                Shader.TileMode.CLAMP));
        System.out.println("wwww="+tv2.getWidth());
        addView(tv1);
        addView(tv2);
    }

    private int dp2px(int dp){
       return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }
}
