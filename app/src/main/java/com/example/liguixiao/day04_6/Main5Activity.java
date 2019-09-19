package com.example.liguixiao.day04_6;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.liguixiao.day04_6.MVP.MyServer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main5Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 上传
     */
    private Button mUpload;
    /**
     * 下载
     */
    private Button mDownload;
    private ProgressBar mProgress;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initView();
    }

    private void initView() {
        mUpload = (Button) findViewById(R.id.upload);
        mUpload.setOnClickListener(this);
        mDownload = (Button) findViewById(R.id.download);
        mDownload.setOnClickListener(this);
        EventBus.getDefault().register(this);
        mProgress = (ProgressBar) findViewById(R.id.progress);
        mTv = (TextView) findViewById(R.id.tv);
        mProgress.setMax(100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.upload:
                Intent intent3 = new Intent(Intent.ACTION_PICK);
                intent3.setType("video/*");
                startActivityForResult(intent3, 1);
                break;

            case R.id.download:
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri data1 = data.getData();

        ContentResolver contentResolver = getContentResolver();
        Cursor query = contentResolver.query(data1, null, null, null, null, null);
        boolean b = query.moveToFirst();
        if (b) {
            String data2 = query.getString(query.getColumnIndex("_data"));
            try {
                upload(data2);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void upload(String s) throws UnsupportedEncodingException {

        File file = new File(s);
        String fileName = URLEncoder.encode(file.getName(), "utf-8");

        Retrofit build = new Retrofit.Builder()
                .baseUrl("https://sm.ms/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        UpDateServer upDateServer = build.create(UpDateServer.class);
        MediaType parse = MediaType.parse("application/octet-stream");
        RequestBody requestBody = RequestBody.create(parse, file);
        MultipartBody.Part smfile = MultipartBody.Part.createFormData("smfile", fileName, requestBody);

        MediaType parse1 = MediaType.parse("text/plain");
        RequestBody json = RequestBody.create(parse1, "json");
        Observable<Results> resultsObservable = upDateServer.upLoad(smfile, json);
        resultsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Results>() {
                    @Override
                    public void accept(Results results) throws Exception {
                        String url = results.getData().getUrl();
                        Log.i("tag", url);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void data(Integer i) {
        mTv.setText("已下载"+i+"%");
        mProgress.setProgress(i);
    }
}
