package com.example.liguixiao.day04_6.MVP;

import java.util.List;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class MainPersenter {

    private Persenter p;

    public MainPersenter(Persenter p) {
        this.p = p;
    }

    public boolean isAttach() {
        return p != null;
    }

    public void getModel() {
        MainModel mainModel = new MainModel();
        mainModel.getData(new Model() {
            @Override
            public void sendData(List<RootBeans.DataBean.DatasBean> datas) {
                if (isAttach()){
                    p.sendData(datas);
                }
            }

            @Override
            public void sendBanner(List<BannerRootBeans.DataBean> data) {
                if (isAttach()){
                    p.sendBanner(data);
                }
            }
        });
    }

    public void detach(){
        p = null;
    }
}
