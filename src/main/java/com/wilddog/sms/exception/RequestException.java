package com.wilddog.sms.exception;

/**
 * Created by wilddog on 17/5/4.
 */
public class RequestException extends RuntimeException{

    private static final long serialVersionUID = 5288664665657045442L;

    public RequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestException(String message) {
        super(message);
    }
}
