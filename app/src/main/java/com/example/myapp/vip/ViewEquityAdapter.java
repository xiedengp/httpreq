package com.example.myapp.vip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapp.R;


public class ViewEquityAdapter extends VipAdapter{
    private Context context;
    private String[] strings = {"权益名称","111","222","333","444","555","666","777","888"};
    private int color;
    public ViewEquityAdapter(Context context, int color){
        this.context = context;
        this.color = color;
    }
    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public View getChildView(int position, ViewGroup group) {
        TextView view = (TextView) LayoutInflater.from(context).inflate(R.layout.item_vip_equity,group,false);
        view.setText(strings[position]);
        view.setBackgroundColor(color);
        return view;
    }
}
