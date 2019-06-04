package com.jason.androidhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            conn.setConnectTimeout(5000);

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

    /**
     * 返回这个URL的图片内容
     *
     * @param sendUrl
     * @return
     */
    public static Bitmap loadImage(String sendUrl) {
        try {
            Log.d("loadImage:", sendUrl);
            URL url = new URL(sendUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            //设置5秒超时
            conn.setConnectTimeout(5000);

            //设置请求方式
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();

            String fileName = String.valueOf(System.currentTimeMillis());
            FileOutputStream outputStream = null;
            File fileDownload = null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File parent = Environment.getExternalStorageDirectory();
                fileDownload = new File(parent, fileName);
                outputStream = new FileOutputStream(fileDownload);
            }
            byte[] bytes = new byte[2 * 1024];
            int lens;
            if (outputStream != null) {
                while ((lens = stream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, lens);
                }
                return BitmapFactory.decodeFile(fileDownload.getAbsolutePath());
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
