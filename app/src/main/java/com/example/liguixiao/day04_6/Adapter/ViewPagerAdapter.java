package com.example.liguixiao.day04_6.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.liguixiao.day04_6.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<String> list;

    public ViewPagerAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        String s = list.get(position);
        View view = View.inflate(context, R.layout.viewpagerlayout,null);
        ImageView viewById = view.findViewById(R.id.viewpager_img);
        Glide.with(context).load(s).into(viewById);
        Integer i = position;
        EventBus.getDefault().post(i);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = View.inflate(context, R.layout.viewpagerlayout,null);
        container.removeView(view);
    }
}
