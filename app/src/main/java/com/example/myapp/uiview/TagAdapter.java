package com.example.myapp.uiview;

import android.view.View;
import android.view.ViewGroup;

public abstract class TagAdapter {

    public abstract int getItemCount();

    public abstract View childView(int item, ViewGroup group);


}
