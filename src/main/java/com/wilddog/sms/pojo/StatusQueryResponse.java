package com.wilddog.sms.pojo;

import java.util.List;
import java.util.Map;

/**
 * Created by wilddog on 17/5/4.
 */
public class StatusQueryResponse extends BaseResponse{

    private String status;

    private List<StatusModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<StatusModel> getData() {
        return data;
    }

    public void setData(List<StatusModel> data) {
        this.data = data;
    }

    public StatusQueryResponse(boolean success, WilddogError wilddogError) {
        super(success, wilddogError);
    }

    public StatusQueryResponse(boolean success, String status, List<StatusModel> data, WilddogError wilddogError) {
        super(success, wilddogError);
        this.status = status;
        this.data = data;
    }
}
