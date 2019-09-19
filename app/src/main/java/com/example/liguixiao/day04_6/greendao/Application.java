package com.example.liguixiao.day04_6.greendao;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by liguixiao on 2019/9/15.
 */

public class Application extends android.app.Application{

    public static DaoSession session;

    @Override
    public void onCreate() {
        super.onCreate();
        session = getSession();

    }

    private DaoSession getSession() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "my.db");
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession;
    }
}
