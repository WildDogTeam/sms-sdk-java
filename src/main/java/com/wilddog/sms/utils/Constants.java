package com.wilddog.sms.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by wilddog on 17/5/4.
 */
public class Constants {

    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public static final String SEND_NOTIFY_URL = "https://sms.wilddog.com/api/v1/%s/notify/send";

    public static final String SEND_CODE_URL = "https://sms.wilddog.com/api/v1/%s/code/send";

    public static final String CHECK_CODE_URL = "https://sms.wilddog.com/api/v1/%s/code/check";

    public static final String QUERY_STATUS_URL = "https://sms.wilddog.com/api/v1/%s/status?";

    public static final String QUERY_BALANCE_URL = "https://sms.wilddog.com/api/v1/%s/getBalance?";

    public static final String WILDDOG_SMS_USER_AGENT = "wilddog-sms-java/1.0.1";
}
