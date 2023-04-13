package com.example.myapp.screen;

import android.view.View;
import android.view.ViewGroup;

public abstract class MenuAdapter {

    public abstract int getCount();

    public abstract View getChildView(int position, ViewGroup group);

    public abstract View getContentView(int position, ViewGroup group);

    public void openTab(View tabChildView) {
    }

    public void closeTab(View tabChildView) {
    }
}
