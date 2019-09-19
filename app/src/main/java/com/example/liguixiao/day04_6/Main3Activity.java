package com.example.liguixiao.day04_6;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private LinearLayout mLinearlayout;
    private NavigationView mNavigation;
    private DrawerLayout mDrawerlayout;
    private Toolbar mToolbar;
    private ViewPager mViewpager;
    private TabLayout mTablayout;
    private CollectFragment collectFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {
        mLinearlayout = (LinearLayout) findViewById(R.id.linearlayout);
        mNavigation = (NavigationView) findViewById(R.id.navigation);
        mDrawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("李桂啸");
        setSupportActionBar(mToolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerlayout, mToolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        mDrawerlayout.addDrawerListener(actionBarDrawerToggle);

        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        mTablayout = (TabLayout) findViewById(R.id.tablayout);

        HomeFragment homeFragment = new HomeFragment();
        collectFragment = new CollectFragment();
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(homeFragment);
        fragments.add(collectFragment);


        com.example.liguixiao.day04_6.Adapter.FragmentPagerAdapter fragmentPagerAdapter = new com.example.liguixiao.day04_6.Adapter.FragmentPagerAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(fragmentPagerAdapter);

        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.getTabAt(0).setText("首页");
        mTablayout.getTabAt(1).setText("收藏");

        View headerView = mNavigation.getHeaderView(0);
        View viewById = headerView.findViewById(R.id.image);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this,Main4Activity.class));
            }
        });

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.broad:

                        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(Main3Activity.this);

                        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                String mess = intent.getStringExtra("mess");
                                if (mess.equals("机器人")){
                                    startActivity(new Intent(Main3Activity.this,Main4Activity.class));
                                }
                            }
                        };

                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction("123123");
                        instance.registerReceiver(broadcastReceiver,intentFilter);

                        Intent intent = new Intent();
                        intent.setAction("123123");
                        intent.putExtra("mess","机器人");
                        instance.sendBroadcast(intent);
                        break;

                    case R.id.notification:

                        NotificationManager systemService = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                        Intent intent1 = new Intent(Main3Activity.this, Main4Activity.class);
                        PendingIntent activity = PendingIntent.getActivity(Main3Activity.this, 99, intent1, PendingIntent.FLAG_CANCEL_CURRENT);

                        Notification build = new NotificationCompat.Builder(Main3Activity.this)
                                .setContentTitle("百度")
                                .setContentText("我是百度李桂啸")
                                .setSmallIcon(R.mipmap.lia)
                                .setContentIntent(activity)
                                .setAutoCancel(true)
                                .build();

                        systemService.notify(1,build);

                        break;

                    case R.id.upload:
                        startActivity(new Intent(Main3Activity.this,Main5Activity.class));
                        break;

                    case R.id.download:
                        startActivity(new Intent(Main3Activity.this,Main5Activity.class));
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"Linearlayout");
        menu.add(1,2,2,"Gridlayout");
        menu.add(1,3,3,"StaggeredGridlayout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                collectFragment.mRecycler.setLayoutManager(new LinearLayoutManager(this));
                break;

            case 2:
                collectFragment.mRecycler.setLayoutManager(new GridLayoutManager(this,2));
                break;

            case 3:
                collectFragment.mRecycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
