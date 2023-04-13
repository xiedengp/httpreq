package com.example.myapp.mode.model;

import com.example.myapp.bean.GoodsBean;

import java.util.List;

public interface IGoodMode {

    void showGoodView(OnLoadListener loadListener);
    interface  OnLoadListener{
        void onComplete(List<GoodsBean> list);
        void onError(String str);

    }
}
