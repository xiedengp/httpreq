package com.example.myapp.mode.presenter;

import com.example.myapp.bean.GoodsBean;
import com.example.myapp.mode.model.GoodMode;
import com.example.myapp.mode.model.IGoodMode;
import com.example.myapp.mode.view.IGoodsView;

import java.util.List;

public class GoodPresenter<T extends IGoodsView> {
    GoodMode goodMode = new GoodMode();
    IGoodsView iGoodsView;

    public GoodPresenter(IGoodsView iGoodsView) {
        this.iGoodsView = iGoodsView;
    }


    //具体业务逻辑
    private void fetch() {
        if (goodMode != null && iGoodsView != null)
            goodMode.showGoodView(new IGoodMode.OnLoadListener() {
                @Override
                public void onComplete(List<GoodsBean> list) {
                    iGoodsView.showGoodView(list);
                }

                @Override
                public void onError(String str) {

                }
            });
    }
}
