package com.example.liguixiao.day04_6;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.liguixiao.day04_6.Adapter.CollectAdapter;
import com.example.liguixiao.day04_6.greendao.Application;
import com.example.liguixiao.day04_6.greendao.DaoSession;
import com.example.liguixiao.day04_6.greendao.sqlits;
import com.example.liguixiao.day04_6.greendao.sqlitsDao;

import java.util.List;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class CollectFragment extends Fragment {

    private View view;
    public static RecyclerView mRecycler;
    private com.example.liguixiao.day04_6.greendao.sqlitsDao sqlitsDao;
    private int index;
    private List<com.example.liguixiao.day04_6.greendao.sqlits> sqlits;
    private CollectAdapter collectAdapter;
    private Intent intent;
    private com.example.liguixiao.day04_6.greendao.sqlits sqlit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.collectfragmentlayout, null);
        initView(view);
        return view;
    }


    private void initView(View view) {
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        DaoSession session = Application.session;
        sqlitsDao = session.getSqlitsDao();
        registerForContextMenu(mRecycler);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            sqlits = sqlitsDao.loadAll();
            collectAdapter = new CollectAdapter(getActivity(), sqlits);
            mRecycler.setAdapter(collectAdapter);
            collectAdapter.setU(new CollectAdapter.User() {
                @Override
                public void onLongClick(int position) {
                    index = position;
                }
            });
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(1,1,1,"删除");
        menu.add(1,2,2,"修改");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        sqlit = this.sqlits.get(index);
        switch (item.getItemId()){
            case 1:
                sqlitsDao.delete(sqlit);
                sqlits.remove(index);
                collectAdapter.notifyDataSetChanged();
                break;

            case 2:
                String title = sqlit.getTitle();
                String pic = sqlit.getPic();
                intent = new Intent(getActivity(), UpDateActivity.class);
                intent.putExtra("title",title);
                intent.putExtra("pic",pic);
                startActivityForResult(intent,100);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&& resultCode==200){
            String t = data.getStringExtra("t");
            sqlit.setTitle(t);
            sqlitsDao.update(sqlit);
            sqlits.set(index,sqlit);
            collectAdapter.notifyDataSetChanged();
        }
    }
}
