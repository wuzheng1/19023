package com.example.liguixiao.day04_6;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread() {
            @Override
            public void run() {
                super.run();

                File externalStorageDirectory = Environment.getExternalStorageDirectory();
                File liguixiao = new File(externalStorageDirectory, "liguixiao");

                OkHttpClient build = new OkHttpClient.Builder().build();
                Request build1 = new Request.Builder()
                        .get()
                        .url("http://cdn.banmi.com/banmiapp/apk/banmi_330.apk")
                        .build();
                Call call = build.newCall(build1);
                try {
                    Response execute = call.execute();
                    ResponseBody body = execute.body();
                    //文件总大小
                    long l = body.contentLength();
                    InputStream inputStream = body.byteStream();
                    byte[] bytes = new byte[1024];
                    int len = inputStream.read(bytes);
                    FileOutputStream fileOutputStream = new FileOutputStream(liguixiao,true);
                    while (len != -1) {
                        fileOutputStream.write(bytes, 0, len);
                        long length = liguixiao.length();
                        Integer random = Math.round(length *100/ l );

                        Log.i("tag", random+"");
                        EventBus.getDefault().post(random);
                        len = inputStream.read(bytes);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
