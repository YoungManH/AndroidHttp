package com.jason.androidhttp.Util;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {
    private static OkHttpClient mOkHttpClient = null;

    public static void init() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .build();
        }
    }

    public static void get(String url, OkHttpCallBack okHttpCallBack) {
        try {
            Call call = null;
            Request request = new Request.Builder().url(url).build();
            call = mOkHttpClient.newCall(request);
            call.enqueue(okHttpCallBack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
