package com.example.myapp.uiview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

public class SampleTitleBehavior extends CoordinatorLayout.Behavior<View> {
    private float deltaY;
    public SampleTitleBehavior(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof RecyclerView;
    }



    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        if(deltaY == 0){
            deltaY = dependency.getY() - child.getHeight();
        }

        float dy = dependency.getY() - child.getHeight();
        dy = dy < 0 ? 0 : dy;
        float y = -(dy / deltaY) * child.getHeight();
        System.out.println("dy->"+dy+"--->"+child.getHeight());
        child.setTranslationY(y);

        return true;
    }

}
