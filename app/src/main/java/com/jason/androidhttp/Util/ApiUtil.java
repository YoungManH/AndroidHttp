package com.jason.androidhttp.Util;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;

public abstract class ApiUtil {
    private String mStatus;
    private ApiListener mApiListener = null;
    private OkHttpCallBack mSendListener = new OkHttpCallBack() {
        @Override
        public void onSuccess(Call call, JSONObject response) {
            mStatus = response.optString("status");
            if (isSuccess()) {
                try {
                    parseData(response);
                    mApiListener.success(ApiUtil.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                mApiListener.failure(ApiUtil.this);
            }
        }

        @Override
        public void onFailure(Call call, IOException e) {
            mApiListener.failure(ApiUtil.this);
        }
    };

    public boolean isSuccess() {
        return "0".equals(mStatus) || "200".equals(mStatus);
    }

    protected abstract void parseData(JSONObject jsonObject) throws Exception;

    protected abstract String getUrl();
}
