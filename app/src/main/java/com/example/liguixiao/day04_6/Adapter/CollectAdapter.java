package com.example.liguixiao.day04_6.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.liguixiao.day04_6.R;
import com.example.liguixiao.day04_6.greendao.sqlits;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder>{

    private Context context;
    private List<sqlits> sqlit;

    public CollectAdapter(Context context, List<sqlits> sqlit) {
        this.context = context;
        this.sqlit = sqlit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recyclerfirstlayout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv.setText(sqlit.get(position).getTitle());
        Glide.with(context).load(sqlit.get(position).getPic()).into(holder.img);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                u.onLongClick(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return sqlit.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.recyclerfirst_img);
            tv = itemView.findViewById(R.id.recyclerfirst_tv);
        }
    }

    public User u;

    public void setU(User u) {
        this.u = u;
    }

    public interface User{
        void onLongClick(int position);
    }
}
