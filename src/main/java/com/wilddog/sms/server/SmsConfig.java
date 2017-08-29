package com.wilddog.sms.server;

/**
 * Created by wangxiaoliang on 17/5/4.
 */
public class SmsConfig {

    private String appId;

    private String smsKey;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSmsKey() {
        return smsKey;
    }

    public void setSmsKey(String smsKey) {
        this.smsKey = smsKey;
    }

    public SmsConfig(String appId, String smsKey) {
        this.appId = appId;
        this.smsKey = smsKey;
    }
}
