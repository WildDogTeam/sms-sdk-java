package com.wilddog.sms.pojo;

/**
 * Created by wilddog on 17/5/4.
 */
public class SmsResponse extends BaseResponse{

    private String rrid;

    public String getRrid() {
        return rrid;
    }

    public void setRrid(String rrid) {
        this.rrid = rrid;
    }

    public SmsResponse(boolean success, WilddogError wilddogError, String rrid) {
        super(success, wilddogError);
        this.rrid = rrid;
    }

}

