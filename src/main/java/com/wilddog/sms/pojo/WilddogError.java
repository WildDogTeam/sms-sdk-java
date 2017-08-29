package com.wilddog.sms.pojo;

/**
 * Created by wilddog on 17/5/4.
 */
public class WilddogError {

    private String errcode;

    private String message;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WilddogError(String errcode, String message) {
        this.errcode = errcode;
        this.message = message;
    }
}
