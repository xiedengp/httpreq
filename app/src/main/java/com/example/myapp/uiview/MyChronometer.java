package com.example.myapp.uiview;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Chronometer;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

public class MyChronometer extends Chronometer implements DefaultLifecycleObserver {
    public static long elapsedTime = 0;
    public MyChronometer(Context context) {
        super(context);
    }

    public MyChronometer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyChronometer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.d("TAG", "onResume");
        setBase(SystemClock.elapsedRealtime()-elapsedTime);
        start();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onPause(owner);
        elapsedTime = SystemClock.elapsedRealtime() - getBase();
        stop();
    }
}
