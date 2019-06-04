package com.jason.androidhttp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.okhttp.OkHttpConnection;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView = null;

    private SendUrlTest mSendUrlTest = null;
    private LoadImageUrl mLoadImageUrl = null;

    private ImageView mImageView = null;

    private  static final int REQUEST_EXTERNAL_STORAGE = 1;
    private  static String[] PERMISSIONS_STORGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        mImageView = (ImageView) findViewById(R.id.img);
//        mSendUrlTest = new SendUrlTest("https://www.baidu.com");
//        mSendUrlTest.execute();
        verifyStoragePerssion(this);
        mLoadImageUrl = new LoadImageUrl("https://img2.mukewang.com/5b8cd82d0001c70702000200-140-140.jpg");
        mLoadImageUrl.execute();

    }

    @Override
    protected void onDestroy() {
        
        super.onDestroy();
        if (mSendUrlTest != null) {
            mSendUrlTest.cancel(true);
        }
        if (mLoadImageUrl != null) {
            mLoadImageUrl.cancel(true);
        }
    }

    private class SendUrlTest extends AsyncTask<Void, Void, String> {

        public String mUrl;

        public SendUrlTest(String url) {
            mUrl = url;
        }

        @Override
        protected String doInBackground(Void... voids) {
            return HttpUtil.sendUrl(mUrl);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mWebView.loadData(s, "text/html;charset=utf-8", null);
        }
    }

    public class LoadImageUrl extends AsyncTask<Void, Void, Bitmap> {
        public String mUrl;

        public LoadImageUrl(String url) {
            mUrl = url;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            return HttpUtil.loadImage(mUrl);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            }
        }
    }

    public  static void verifyStoragePerssion(Activity activity){
        int writePerssion = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPerssion = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(writePerssion != PackageManager.PERMISSION_GRANTED || readPerssion != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORGE, REQUEST_EXTERNAL_STORAGE);
        }
    }
}
