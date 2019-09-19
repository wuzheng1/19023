package com.example.liguixiao.day04_6.MVP;

import java.util.List;

/**
 * Created by liguixiao on 2019/9/15.
 */

public interface Model {
    void sendData(List<RootBeans.DataBean.DatasBean> datas);
    void sendBanner(List<BannerRootBeans.DataBean> data);
}
