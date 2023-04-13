package com.example.myapp.goodstyle;

import android.animation.TypeEvaluator;
import android.graphics.PointF;


public class LoveTypeEvaluator implements TypeEvaluator<PointF> {
    private final PointF point1;
    private final PointF point2;

    public LoveTypeEvaluator(PointF point1, PointF point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public PointF evaluate(float t, PointF point0, PointF point3) {
        PointF pointF = new PointF();
        //贝塞尔三阶公式套用
        pointF.x = point0.x*(1-t)*(1-t)*(1-t)
                +3*point1.x*t*(1-t)*(1-t)
                +3*point2.x*t*t*(1-t)
                +point3.x*t*t*t;

        pointF.y = point0.y*(1-t)*(1-t)*(1-t)
                +3*point1.y*t*(1-t)*(1-t)
                +3*point2.y*t*t*(1-t)
                +point3.y*t*t*t;

        return pointF;
    }
}
