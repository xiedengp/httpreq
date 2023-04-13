package com.example.myapp.screen;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp.R;

public class ListDataScreenAdapter extends MenuAdapter{
    private final String[] strings = {"我的","消息","发现","更多"};
    private final Context context;

    public ListDataScreenAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public View getChildView(int position, ViewGroup group) {
        TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tab_title,group,false);
        view.setText(strings[position]);
        view.setTextColor(Color.BLACK);
        return view;
    }

    @Override
    public View getContentView(int position, ViewGroup group) {
        TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.item_list_content,group,false);
        view.setText(strings[position]);
        view.setTextColor(Color.BLACK);
        return view;
    }

    @Override
    public void openTab(View tabChildView) {
        TextView textView = (TextView) tabChildView;
        textView.setTextColor(Color.RED);
    }

    @Override
    public void closeTab(View tabChildView) {
        TextView textView = (TextView) tabChildView;
        textView.setTextColor(Color.BLACK);
    }
}
