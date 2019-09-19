package com.example.liguixiao.day04_6.Adapter;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liguixiao.day04_6.MVP.BannerRootBeans;
import com.example.liguixiao.day04_6.MVP.RootBeans;
import com.example.liguixiao.day04_6.R;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<BannerRootBeans.DataBean> bannerData;
    private ArrayList<RootBeans.DataBean.DatasBean> recyData;

    public RecyclerAdapter(Context context, ArrayList<BannerRootBeans.DataBean> bannerData, ArrayList<RootBeans.DataBean.DatasBean> recyData) {
        this.context = context;
        this.bannerData = bannerData;
        this.recyData = recyData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            View view = View.inflate(context, R.layout.recyclerbannerlayout, null);
            return new BannerViewHolder(view);
        } else if (viewType == 2) {
            View view = View.inflate(context, R.layout.recyclerfirstlayout, null);
            return new FirstViewHolder(view);
        } else {
            View view = View.inflate(context, R.layout.recyclernextlayout, null);
            return new NextViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (bannerData.size() > 0) {
            position = position - 1;
        }
        if (type == 1) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.banner.setImages(bannerData).setImageLoader(new Img()).start();
        } else if (type == 2) {
            FirstViewHolder firstViewHolder = (FirstViewHolder) holder;
            firstViewHolder.tv.setText(recyData.get(position).getTitle());
            Glide.with(context).load(recyData.get(position).getEnvelopePic()).into(firstViewHolder.img);
        } else {
            NextViewHolder nextViewHolder = (NextViewHolder) holder;
            nextViewHolder.tv.setText(recyData.get(position).getTitle());
            Glide.with(context).load(recyData.get(position).getEnvelopePic()).into(nextViewHolder.img);
        }

        final int finalPosition = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.onClick(finalPosition);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                u.onLongClick(finalPosition);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if (bannerData.size() > 0) {
            return recyData.size() + 1;
        } else {
            return recyData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (bannerData.size() > 0 && position == 0) {
            return 1;
        } else if (position % 2 == 0) {
            return 2;
        } else {
            return 3;
        }
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {

        Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.recyclerbanner_banner);
        }
    }

    class FirstViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tv;

        public FirstViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.recyclerfirst_img);
            tv = itemView.findViewById(R.id.recyclerfirst_tv);
        }
    }

    class NextViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tv;

        public NextViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.recyclernext_img);
            tv = itemView.findViewById(R.id.recyclernext_tv);
        }
    }

    class Img extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            BannerRootBeans.DataBean d = (BannerRootBeans.DataBean) path;
            Glide.with(context).load(d.getImagePath()).into(imageView);
        }
    }

    public User u;

    public void setU(User u) {
        this.u = u;
    }

    public interface User {
        void onClick(int positon);

        void onLongClick(int postion);
    }
}
