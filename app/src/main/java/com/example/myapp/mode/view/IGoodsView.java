package com.example.myapp.mode.view;

import com.example.myapp.bean.GoodsBean;

import java.util.List;

public interface IGoodsView extends BaseView{
    void showGoodView(List<GoodsBean> list);
}
