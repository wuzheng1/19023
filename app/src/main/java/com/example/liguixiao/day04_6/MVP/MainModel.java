package com.example.liguixiao.day04_6.MVP;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class MainModel {

    public void getData(final Model m){
        new Thread(){
            @Override
            public void run() {
                super.run();

                Retrofit build = new Retrofit.Builder()
                        .baseUrl("https://www.wanandroid.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                MyServer myServer = build.create(MyServer.class);
                Observable<RootBeans> person = myServer.getPerson();
                person.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<RootBeans>() {
                            @Override
                            public void accept(RootBeans rootBeans) throws Exception {
                                List<RootBeans.DataBean.DatasBean> datas = rootBeans.getData().getDatas();
                                m.sendData(datas);
                            }
                        });

                Observable<BannerRootBeans> banner = myServer.getBanner();
                banner.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BannerRootBeans>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(BannerRootBeans bannerRootBeans) {
                                List<BannerRootBeans.DataBean> data = bannerRootBeans.getData();
                                m.sendBanner(data);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        }.start();
    }
}
