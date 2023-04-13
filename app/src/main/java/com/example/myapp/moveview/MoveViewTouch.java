package com.example.myapp.moveview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.myapp.uiview.MoveBesselView;

//todo 任意按钮 可设置拖拽   监听事件自己完善
public class MoveViewTouch implements View.OnTouchListener {
    private WindowManager windowManager;
    private Context mContext;
    private View fixedView;
    private MoveBesselView besselView;
    private Bitmap bitmap;
    private WindowManager.LayoutParams layoutParams;
    private RectF viewRectF;

    public MoveViewTouch(Context context, View view) {
        fixedView = view;
        mContext = context;
        besselView = new MoveBesselView(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.format = PixelFormat.TRANSPARENT;
        view.post(new Runnable() {
            @Override
            public void run() {
                bitmap = getViewBitmap(fixedView,mContext);
                besselView.setBitmap(bitmap);
            }
        });

        viewRectF = calcViewScreenLocation(view);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        boolean isInViewRect = viewRectF.contains(event.getRawX(), event.getRawY());
        System.out.println("isInViewRect="+event.getAction());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fixedView.setVisibility(View.INVISIBLE);
                windowManager.addView(besselView, layoutParams);
                besselView.initPoint(event.getRawX()-fixedView.getWidth()/2f, event.getRawY()-fixedView.getHeight()/2f);

                break;
            case MotionEvent.ACTION_MOVE:
                besselView.updatePoint(event.getRawX(), event.getRawY());
                break;
            case MotionEvent.ACTION_UP:
                fixedView.setVisibility(View.VISIBLE);
                windowManager.removeView(besselView);
                break;
        }
        return true;
    }

    public static RectF calcViewScreenLocation(View view) {
        int[] location = new int[2];
        // 获取控件在屏幕中的位置，返回的数组分别为控件左顶点的 x、y 的值
        view.getLocationOnScreen(location);
        return new RectF(location[0], location[1], location[0] + view.getWidth(),
                location[1] + view.getHeight());
    }
    private Bitmap getViewBitmap(View view,Context context) {
        if (null == view) {
            return null;
        }
        Bitmap bitmap = null;

        // 步骤一：获取视图的宽和高
        int  width = view.getWidth();
        int height = view.getHeight();

        // 判断背景是否为不透明
        boolean opaque = view.getDrawingCacheBackgroundColor() != 0 || view.isOpaque();
        Bitmap.Config quality= Bitmap.Config.ARGB_8888;


        // 步骤二：生成bitmap
        bitmap = Bitmap.createBitmap(context.getResources().getDisplayMetrics(), width, height, quality);
        bitmap.setDensity(context.getResources().getDisplayMetrics().densityDpi);
        if (opaque) {
            bitmap.setHasAlpha(false);
        }
        boolean clear = view.getDrawingCacheBackgroundColor() != 0;

        // 步骤三：绘制canvas
        Canvas canvas = new Canvas(bitmap);
        if (clear) {
            bitmap.eraseColor(view.getDrawingCacheBackgroundColor());
        }
        view.computeScroll();
        int  restoreCount = canvas.save();
        canvas.translate(view.getScaleX(), view.getScaleY());
        view.draw(canvas);
        canvas.restoreToCount(restoreCount);
        canvas.setBitmap(null);
        return bitmap;
    }
}
