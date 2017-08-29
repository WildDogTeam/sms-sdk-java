package com.wilddog.sms.pojo;

/**
 * Created by wangxiaoliang on 17/5/4.
 */
public abstract class BaseResponse {

    private boolean success;

    private WilddogError wilddogError;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public WilddogError getWilddogError() {
        return wilddogError;
    }

    public void setWilddogError(WilddogError wilddogError) {
        this.wilddogError = wilddogError;
    }

    public BaseResponse(boolean success, WilddogError wilddogError) {
        this.success = success;
        this.wilddogError = wilddogError;
    }
}
