package com.jason.androidhttp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private WebView mWebView = null;

    private SendUrlTest mSendUrlTest = null;
    private LoadImageUrl mLoadImageUrl = null;

    private ImageView mImageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        mImageView = (ImageView) findViewById(R.id.img);
//        mSendUrlTest = new SendUrlTest("https://www.baidu.com");
//        mSendUrlTest.execute();
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
}
