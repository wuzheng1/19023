package com.example.liguixiao.day04_6;

import java.util.Observable;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by liguixiao on 2019/9/15.
 */

public interface UpDateServer {
    @Multipart
    @POST("api/upload")
    @Headers({"User-Agent: Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36"})
    io.reactivex.Observable<Results> upLoad(@Part MultipartBody.Part filePart,@Part("format") RequestBody body);

}
