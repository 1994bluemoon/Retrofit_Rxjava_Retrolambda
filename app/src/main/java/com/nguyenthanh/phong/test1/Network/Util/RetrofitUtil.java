package com.nguyenthanh.phong.test1.Network.Util;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nguyenthanh.phong.test1.BuildConfig.API_KEY;
import static com.nguyenthanh.phong.test1.BuildConfig.BASE_URL;

public class RetrofitUtil {

    public static Retrofit create() {
        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();
    }

    private static OkHttpClient client() {
        Interceptor interceptor = chain -> {
            Request newRequest = chain.request();

            HttpUrl httpUrl = newRequest
                    .url()
                    .newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .build();

            newRequest = newRequest.newBuilder()
                    .addHeader("User-Agent", "Retrofit-Sample-App")
                    .url(httpUrl)
                    .build();

            return chain.proceed(newRequest);
        };

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        return builder.build();
    }
}
