package com.example.myapp.vip;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;

public class VipEquityView extends HorizontalScrollView {
    private int row = 5;

    private float chileViewWidth;
    private int viewWidth;
    private LinearLayoutCompat layoutCompat;

    public void setRow(int row) {
        this.row = row;
        invalidate();
    }

    public VipEquityView(@NonNull Context context) {
        this(context, null);
    }

    public VipEquityView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VipEquityView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        initLayout(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        chileViewWidth = MeasureSpec.getSize(widthMeasureSpec) / 6.7f;
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(viewWidth, MeasureSpec.getSize(heightMeasureSpec));
        initLayout(getContext());
    }


    private void initLayout(Context context) {
        if (layoutCompat != null) return;
        layoutCompat = new LinearLayoutCompat(context);
        LinearLayoutCompat.LayoutParams layoutParamsOut = new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        layoutCompat.setLayoutParams(layoutParamsOut);
        layoutCompat.setBackgroundColor(Color.BLUE);
        layoutCompat.setOrientation(LinearLayoutCompat.HORIZONTAL);
        layoutCompat.setGravity(Gravity.CENTER);

        for (int i = 0; i < row; i++) {
            LinearLayoutCompat layout = new LinearLayoutCompat(context);
            layout.setOrientation(LinearLayoutCompat.VERTICAL);
            LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(0, LayoutParams.WRAP_CONTENT);
            if (row > 6) {
                System.out.println("style------>1");
                if (i == 0)
                    layoutParams.width = (int) (chileViewWidth * 1.6f);
                else
                    layoutParams.width = (int) chileViewWidth;
            } else {
                System.out.println("style------>2");
                float denominator = row + 0.6f;
                if (i == 0)
                    layoutParams.width = (int) (viewWidth / denominator * 1.6);
                else if(i==row-1){
                    //最后一个占满 int有误差
                    layoutParams.width = viewWidth - (int) (viewWidth / denominator * 1.6)- ((int) (viewWidth / denominator))*(row-2);
                }else
                    layoutParams.width = (int) (viewWidth / denominator);
            }

            layout.setLayoutParams(layoutParams);
            layout.setAlpha(0.8f - (i / 10f));
//            layout.setBackgroundColor(Color.RED);
            layoutCompat.addView(layout);

        }

        VipEquityView.this.addView(layoutCompat);
//        setBackgroundColor(Color.YELLOW);
    }

    public void setAdapter(VipAdapter adapter, int rowPosition) {
        if (rowPosition >= row) return;
        if (adapter == null) return;
        post(() -> {
            int count = adapter.getCount();
            LinearLayoutCompat layout = (LinearLayoutCompat) layoutCompat.getChildAt(rowPosition);
            for (int i = 0; i < count; i++) {
                layout.addView(adapter.getChildView(i, layout));
            }
        });

    }
}
