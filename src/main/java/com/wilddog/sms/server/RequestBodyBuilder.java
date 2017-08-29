package com.wilddog.sms.server;

import com.wilddog.sms.utils.Constants;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wilddog on 17/5/4.
 */
public class RequestBodyBuilder {

    public static RequestBody buildNotifySendRequestBody(List<String> mobiles, String templateId, List<String> params, long timestamp, String secret) {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("mobiles", Constants.GSON.toJson(mobiles));
        paramsMap.put("params", Constants.GSON.toJson(params));
        paramsMap.put("templateId", templateId);
        paramsMap.put("timestamp", String.valueOf(timestamp));
        return buildRequestBody(paramsMap, secret);
    }

    public static RequestBody buildCodeSendRequestBody(String mobile, String templateId, List<String> params, long timestamp, String secret) {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("mobile", mobile);
        if (params != null) {
            paramsMap.put("params", Constants.GSON.toJson(params));
        }
        paramsMap.put("templateId", templateId);
        paramsMap.put("timestamp", String.valueOf(timestamp));
        return buildRequestBody(paramsMap, secret);
    }

    public static RequestBody buildCodeCheckRequestBody(String mobile, String code, long timestamp, String secret) {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("code", code);
        paramsMap.put("mobile", mobile);
        paramsMap.put("timestamp", String.valueOf(timestamp));
        return buildRequestBody(paramsMap, secret);
    }

    public static String buildStatusQueryParam(String rrid, String secret) {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("rrid", rrid);
        return "signature=" + calculateSignature(paramsMap, secret) + "&" + "rrid=" + rrid;
    }

    public static String buildBalanceQueryParam(long timestamp, String secret) {
        Map<String, String> paramsMap = new LinkedHashMap<String, String>();
        paramsMap.put("timestamp", String.valueOf(timestamp));
        return "signature=" + calculateSignature(paramsMap, secret) + "&" + "timestamp=" + timestamp;
    }

    private static RequestBody buildRequestBody(Map<String, String> sortedMap, String secret) {
        StringBuilder sb = new StringBuilder();
        FormBody.Builder formBody = new FormBody.Builder();
        for (String s : sortedMap.keySet()) {
            System.out.println(s + "  " + sortedMap.get(s));
            formBody.add(s, sortedMap.get(s) + "");
            sb.append(String.format("%s=%s&", s, sortedMap.get(s)));
        }
        sb.append(secret);
        byte[] sig = DigestUtils.sha256(sb.toString().getBytes());
        String signature =  Hex.encodeHexString(sig);
        return formBody.add("signature", signature).build();
    }

    private static String calculateSignature(Map<String, String> sortedMap, String secret) {
        StringBuilder sb = new StringBuilder();
        FormBody.Builder formBody = new FormBody.Builder();
        for (String s : sortedMap.keySet()) {
            formBody.add(s, sortedMap.get(s) + "");
            sb.append(String.format("%s=%s&", s, sortedMap.get(s)));
        }
        sb.append(secret);
        byte[] sig = DigestUtils.sha256(sb.toString().getBytes());
        return Hex.encodeHexString(sig);
    }

}
