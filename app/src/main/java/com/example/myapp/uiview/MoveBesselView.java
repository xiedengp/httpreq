package com.example.myapp.uiview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapp.moveview.MoveViewTouch;

public class MoveBesselView extends View {
    //移动点 和 固定点
    private PointF moveCirclePoint, fixedCirclePoint;
    private final Paint paint;
    private int moveRadius = 20;
    private int fixedRadiusMax = 17;
    private int fixedRadiusMin = 5;
    private int currentFixedRadius;

    private Context context;

    private boolean fixedIsShow = false; //固定圆是否消失

    private Bitmap bitmap;

    public MoveBesselView(Context context) {
        this(context, null);
    }

    public MoveBesselView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveBesselView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        context = context;
        moveRadius = dip2px(moveRadius);
        fixedRadiusMax = dip2px(fixedRadiusMax);
        fixedRadiusMin = dip2px(fixedRadiusMin);
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setDither(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (moveCirclePoint == null || fixedCirclePoint == null) {
            return;
        }
        canvas.drawCircle(moveCirclePoint.x, moveCirclePoint.y, moveRadius, paint);

        currentFixedRadius = fixedRadiusMax - getFixedRadius() / 20;
        if (currentFixedRadius > fixedRadiusMin) {
            fixedIsShow = false;
            canvas.drawCircle(fixedCirclePoint.x, fixedCirclePoint.y, currentFixedRadius, paint);
            Path path = getBesselPath();
            canvas.drawPath(path,paint);
        }else {
            fixedIsShow = true;
        }

        if(bitmap!=null){
            canvas.drawBitmap(bitmap,moveCirclePoint.x-bitmap.getWidth()/2,moveCirclePoint.y-bitmap.getHeight()/2,null);
        }


    }

    private Path getBesselPath() {
        Path besselPath = new Path();
        //求斜率
        float dx = moveCirclePoint.x - fixedCirclePoint.x;
        float dy = moveCirclePoint.y - fixedCirclePoint.y;
        float tanA = dy / dx;
        //求得角度
        double a = Math.atan(tanA);

        float p0X = (float) (fixedCirclePoint.x+currentFixedRadius*Math.sin(a));
        float p0Y = (float) (fixedCirclePoint.y-currentFixedRadius*Math.cos(a));

        float p1X = (float) (moveCirclePoint.x+moveRadius*Math.sin(a));
        float p1Y = (float) (moveCirclePoint.y-moveRadius*Math.cos(a));

        float p2X = (float) (moveCirclePoint.x-moveRadius*Math.sin(a));
        float p2Y = (float) (moveCirclePoint.y+moveRadius*Math.cos(a));

        float p3X = (float) (fixedCirclePoint.x-currentFixedRadius*Math.sin(a));
        float p3Y = (float) (fixedCirclePoint.y+currentFixedRadius*Math.cos(a));

        PointF midPoint = getMidPoint();
        besselPath.moveTo(p0X,p0Y);
        besselPath.quadTo(midPoint.x,midPoint.y,p1X,p1Y);
        besselPath.lineTo(p2X,p2Y);
        besselPath.quadTo(midPoint.x,midPoint.y,p3X,p3Y);
        besselPath.close();

        return besselPath;
    }

    private PointF getMidPoint() {
       return new PointF((moveCirclePoint.x+fixedCirclePoint.x)/2,(moveCirclePoint.y+fixedCirclePoint.y)/2);
    }

    private int getFixedRadius() {
        float x = moveCirclePoint.x - fixedCirclePoint.x;
        float y = moveCirclePoint.y - fixedCirclePoint.y;
        return (int) Math.sqrt(x * x + y * y);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                float downX = event.getX();
//                float downY = event.getY();
//                initPoint(downX,downY);
//
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float moveX = event.getX();
//                float moveY = event.getY();
//                updatePoint(moveX,moveY);
//                break;
//            case MotionEvent.ACTION_UP:
//                moveCirclePoint = null;
//                fixedCirclePoint = null;
//                break;
//        }
// //        invalidate();
//        return true;
//    }

    public static void setMoveView(Context context,View view){
        view.setOnTouchListener(new MoveViewTouch(context ,view));
    }

    public void initPoint(float x,float y){
        moveCirclePoint = new PointF(x, y);
        fixedCirclePoint = new PointF(x, y);
        invalidate();
    }

    public void updatePoint(float x,float y){
        moveCirclePoint = new PointF(x, y);
        invalidate();
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
