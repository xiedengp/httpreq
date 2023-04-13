package com.example.myapp.vip;

import android.view.View;
import android.view.ViewGroup;

public abstract class VipAdapter {
    public abstract int getCount();

    public abstract View getChildView(int position, ViewGroup group);
}
