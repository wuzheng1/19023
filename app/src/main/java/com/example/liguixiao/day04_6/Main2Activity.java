package com.example.liguixiao.day04_6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.liguixiao.day04_6.Adapter.ViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewpager;
    private TextView mPage;
    /**
     * 完成
     */
    private Button mOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mPage = (TextView) findViewById(R.id.page);
        mOk = (Button) findViewById(R.id.ok);
        mOk.setOnClickListener(this);

        mPage.setText("1/3");


        ArrayList<String> list = new ArrayList<>();
        list.add("https://wanandroid.com/blogimgs/0b712568-6203-4a03-b475-ff55e68d89e8.jpeg");
        list.add("https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png");
        list.add("https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png");

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, list);
        mViewpager.setAdapter(viewPagerAdapter);

        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPage.setText(position+1+"/3");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ok:
                startActivity(new Intent(this,Main3Activity.class));
                break;
        }
    }


    @Subscribe
    public void show(Integer i){
        //mPage.setText(i+"");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
