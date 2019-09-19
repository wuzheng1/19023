package com.example.liguixiao.day04_6;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.liguixiao.day04_6.Adapter.RecyclerAdapter;
import com.example.liguixiao.day04_6.MVP.BannerRootBeans;
import com.example.liguixiao.day04_6.MVP.MainPersenter;
import com.example.liguixiao.day04_6.MVP.Persenter;
import com.example.liguixiao.day04_6.MVP.RootBeans;
import com.example.liguixiao.day04_6.greendao.Application;
import com.example.liguixiao.day04_6.greendao.DaoSession;
import com.example.liguixiao.day04_6.greendao.sqlits;
import com.example.liguixiao.day04_6.greendao.sqlitsDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class HomeFragment extends Fragment implements Persenter {

    private MainPersenter mainPersenter;
    private ArrayList<BannerRootBeans.DataBean> bannerData;
    private ArrayList<RootBeans.DataBean.DatasBean> recyData;
    private View view;
    private RecyclerView mRecyclerview;
    private RecyclerAdapter recyclerAdapter;
    private PopupWindow popupWindow;
    private int index;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.homefragmentlayout, null);

        //处理全局RXjava异常
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d("tag", throwable.getMessage());
            }
        });

        recyData = new ArrayList<>();
        bannerData = new ArrayList<>();
        mainPersenter = new MainPersenter(this);
        mainPersenter.getModel();

        View pop =View.inflate(getActivity(),R.layout.popwindowlayout,null);
        pop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        View popwindow_collect = pop.findViewById(R.id.popwindow_collect);
        popwindow_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoSession session = Application.session;
                sqlitsDao sqlitsDao = session.getSqlitsDao();
                RootBeans.DataBean.DatasBean datasBean = recyData.get(index);
                sqlits sqlits = new sqlits();
                sqlits.setTitle(datasBean.getTitle());
                sqlits.setPic(datasBean.getEnvelopePic());
                sqlitsDao.insert(sqlits);
                Toast.makeText(getActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
            }
        });

        //popupWindow = new PopupWindow(pop, 200, 200);

        popupWindow = new PopupWindow(pop, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        this.popupWindow.setOutsideTouchable(true);
        this.popupWindow.setBackgroundDrawable(new ColorDrawable());
        this.popupWindow.setAnimationStyle(R.style.popwindow_anim);

        recyclerAdapter = new RecyclerAdapter(getActivity(), bannerData, recyData);
        recyclerAdapter.setU(new RecyclerAdapter.User() {
            @Override
            public void onClick(int positon) {
                index = positon;
                HomeFragment.this.popupWindow.showAtLocation(mRecyclerview, Gravity.CENTER,0,0);
            }

            @Override
            public void onLongClick(int postion) {

            }
        });
        initView(view);
        return view;
    }

    @Override
    public void sendData(List<RootBeans.DataBean.DatasBean> datas) {
        recyData.addAll(datas);
        mRecyclerview.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void sendBanner(List<BannerRootBeans.DataBean> data) {
        bannerData.addAll(data);
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mainPersenter.detach();
    }

    private void initView(View view) {
        mRecyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerview.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
    }
}
