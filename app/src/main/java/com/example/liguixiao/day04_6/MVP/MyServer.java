package com.example.liguixiao.day04_6.MVP;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by liguixiao on 2019/9/15.
 */

public interface MyServer {
    @GET("project/list/1/json?cid=294")
    Observable<RootBeans> getPerson();

    @GET("banner/json")
    Observable<BannerRootBeans> getBanner();
}
