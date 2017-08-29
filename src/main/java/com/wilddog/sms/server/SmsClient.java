package com.wilddog.sms.server;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.wilddog.sms.exception.RequestException;
import com.wilddog.sms.pojo.BalanceResponse;
import com.wilddog.sms.pojo.SmsResponse;
import com.wilddog.sms.pojo.StatusModel;
import com.wilddog.sms.pojo.StatusQueryResponse;
import com.wilddog.sms.pojo.WilddogError;
import com.wilddog.sms.utils.Constants;
import com.wilddog.sms.utils.OkHttpFactory;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wilddog on 17/5/4.
 */
public class SmsClient {

    private SmsConfig smsConfig;

    public SmsClient(SmsConfig smsConfig) {
        this.smsConfig = smsConfig;
    }

    private SmsResponse parseSmsResponse(Response response) throws IOException {
        // deal with success body
        String responseBody = response.body().string();
        JsonObject jsonObject = Constants.GSON.fromJson(responseBody, JsonObject.class);
        JsonObject data = (JsonObject) jsonObject.get("data");
        if (data == null) {
            throw new RequestException("Invalid SmsResponse, missing property 'data'.");
        }
        JsonElement rrid = data.get("rrid");
        if (rrid == null) {
            throw new RequestException("Invalid SmsResponse, missing property 'rrid'.");
        }
        return new SmsResponse(true, null, rrid.getAsString());
    }

    private SmsResponse parseErrorResponse(Response response) throws IOException {
        String responseBody = response.body().string();
        JsonObject jsonObject = Constants.GSON.fromJson(responseBody, JsonObject.class);
        if (!jsonObject.has("errcode")) {
            throw new RequestException("Invalid Response, missing property 'errcode', httpCode is " + response.code());
        }
        return new SmsResponse(false, new WilddogError(jsonObject.get("errcode").getAsString(), jsonObject.get("message").getAsString()), null);
    }

    public SmsResponse sendNotify(List<String> mobiles, String templateId, ArrayList<String> params) throws IOException {
        long timestamp = System.currentTimeMillis();
        RequestBody body = RequestBodyBuilder.buildNotifySendRequestBody(mobiles, templateId, params, timestamp, smsConfig.getSmsKey());

        Request request = new Request.Builder().url(String.format(Constants.SEND_NOTIFY_URL, smsConfig.getAppId())).post(body).header("User-Agent", Constants.WILDDOG_SMS_USER_AGENT).build();

        Response response = OkHttpFactory.getRequestClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            return parseErrorResponse(response);
        }
        return parseSmsResponse(response);
    }

    public SmsResponse sendCode(String mobile, String templateId) throws IOException {
        return sendCode(mobile, templateId, null);
    }

    public SmsResponse sendCode(String mobile, String templateId, List<String> params) throws IOException {
        long timestamp = System.currentTimeMillis();
        RequestBody body = RequestBodyBuilder.buildCodeSendRequestBody(mobile, templateId, params, timestamp, smsConfig.getSmsKey());

        Request request = new Request.Builder().url(String.format(Constants.SEND_CODE_URL, smsConfig.getAppId())).post(body).header("User-Agent", Constants.WILDDOG_SMS_USER_AGENT).build();

        Response response = OkHttpFactory.getRequestClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            return parseErrorResponse(response);
        }
        return parseSmsResponse(response);

    }

    public SmsResponse checkCode(String mobile, String code) throws IOException {
        long timestamp = System.currentTimeMillis();
        RequestBody body = RequestBodyBuilder.buildCodeCheckRequestBody(mobile, code, timestamp, smsConfig.getSmsKey());

        Request request = new Request.Builder().url(String.format(Constants.CHECK_CODE_URL, smsConfig.getAppId())).post(body).header("User-Agent", Constants.WILDDOG_SMS_USER_AGENT).build();

        Response response = OkHttpFactory.getRequestClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            return parseErrorResponse(response);
        }
        // deal with success body
        String responseBody = response.body().string();
        JsonObject jsonObject = Constants.GSON.fromJson(responseBody, JsonObject.class);
        JsonElement status = jsonObject.get("status");
        if (status == null) {
            throw new RequestException("Invalid SmsResponse, missing property 'status'.");
        }
        if (!"ok".equals(status.getAsString())) {
            throw new RequestException("Invalid SmsResponse, status not ok.");
        }
        return new SmsResponse(true, null, null);
    }

    public StatusQueryResponse queryStatus(String rrid) throws IOException {
        Request request = new Request.Builder().url(String.format(Constants.QUERY_STATUS_URL, smsConfig.getAppId()) + RequestBodyBuilder.buildStatusQueryParam(rrid, smsConfig.getSmsKey())).get().header("User-Agent", Constants.WILDDOG_SMS_USER_AGENT).build();

        Response response = OkHttpFactory.getRequestClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            String responseBody = response.body().string();
            JsonObject jsonObject = Constants.GSON.fromJson(responseBody, JsonObject.class);
            if (!jsonObject.has("errcode")) {
                throw new RequestException("Invalid Response, missing property 'errcode', httpCode is " + response.code());
            }
            return new StatusQueryResponse(false, null, null, new WilddogError(jsonObject.get("errcode").getAsString(), jsonObject.get("message").getAsString()));
        }
        // deal with success body
        String responseBody = response.body().string();
        Map<String, Object> resultMap = Constants.GSON.fromJson(responseBody, Map.class);

        StatusQueryResponse statusQueryResponse = new StatusQueryResponse(false, null);
        statusQueryResponse.setSuccess(true);
        statusQueryResponse.setStatus((String) resultMap.get("status"));
        statusQueryResponse.setWilddogError(null);
        String data = Constants.GSON.toJson(resultMap.get("data"));
        List<StatusModel> modelList = Constants.GSON.fromJson(data, new TypeToken<List<StatusModel>>() {
        }.getType());
        statusQueryResponse.setData(modelList);
        return statusQueryResponse;
    }


    public BalanceResponse queryBalance() throws IOException {
        Request request = new Request.Builder().url(String.format(Constants.QUERY_BALANCE_URL, smsConfig.getAppId()) + RequestBodyBuilder.buildBalanceQueryParam(System.currentTimeMillis(), smsConfig.getSmsKey())).get().header("User-Agent", Constants.WILDDOG_SMS_USER_AGENT).build();

        Response response = OkHttpFactory.getRequestClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            String responseBody = response.body().string();
            JsonObject jsonObject = Constants.GSON.fromJson(responseBody, JsonObject.class);
            if (!jsonObject.has("errcode")) {
                throw new RequestException("Invalid Response, missing property 'errcode', httpCode is " + response.code());
            }
            return new BalanceResponse(false, new WilddogError(jsonObject.get("errcode").getAsString(), jsonObject.get("message").getAsString()), null, 0L, 0L);
        }
        // deal with success body
        String responseBody = response.body().string();
        Map<String, Object> resultMap = Constants.GSON.fromJson(responseBody, Map.class);

        BalanceResponse balanceResponse = new BalanceResponse(true, null);
        balanceResponse.setStatus((String) resultMap.get("status"));
        Map<String, Object> data = (Map<String, Object>) resultMap.get("data");
        balanceResponse.setBalance(((Double) data.get("balance")).longValue());
        balanceResponse.setVoucherBalance(((Double) data.get("voucherBalance")).longValue());
        return balanceResponse;
    }
}
