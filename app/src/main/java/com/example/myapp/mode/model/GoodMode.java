package com.example.myapp.mode.model;

import com.example.myapp.bean.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class GoodMode implements IGoodMode{
    @Override
    public void showGoodView(OnLoadListener loadListener) {
        loadListener.onComplete(getGoods());
    }

    /**
     * 真正的网络请求数据
     * @return
     */
    private List<GoodsBean> getGoods(){
        List<GoodsBean> beans = new ArrayList<>();
        return beans;
    }
}
