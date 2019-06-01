package com.jason.androidhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {


    private WebView mWebView = null;

    private SendUrlTest mSendUrlTest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (WebView) findViewById(R.id.webview);
        mSendUrlTest = new SendUrlTest("http://www.baidu.com");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSendUrlTest != null) {
            mSendUrlTest.cancel(true);
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
            mWebView.loadData(s, "text/html","charset=utf-8");
        }
    }
}
