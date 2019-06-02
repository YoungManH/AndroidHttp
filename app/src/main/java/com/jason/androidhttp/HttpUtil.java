package com.jason.androidhttp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {

    /**
     * 返回这个url对应网址的内容
     *
     * @param sendUrl
     * @return
     */
    public static String sendUrl(String sendUrl) {
        try {
            Log.d("HttpUtil:", sendUrl);
            URL url = new URL(sendUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            //设置5秒超时
            conn.setConnectTimeout(15000);

            //设置请求方式
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }
            return buffer.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
