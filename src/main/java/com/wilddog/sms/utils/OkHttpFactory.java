package com.wilddog.sms.utils;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

/**
 * Created by wilddog on 17/5/4.
 */
public class OkHttpFactory {

    private static OkHttpClient requestClient = initClientWithParams(10,5);

    public static OkHttpClient getRequestClient(){
        return requestClient;
    }

    private static OkHttpClient initClientWithParams(int connectTimeout,int maxRequest){

        OkHttpClient client = null;
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(maxRequest);
        dispatcher.setMaxRequestsPerHost(maxRequest);
        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            X509TrustManager x509TrustManager = new X509TrustManager() {
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {}

                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType)
                        throws CertificateException {}

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[] {};
                }
            };
            final TrustManager[] trustAllCerts = new TrustManager[] {x509TrustManager};
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            client = new OkHttpClient.Builder()
                    .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(connectTimeout, TimeUnit.SECONDS)
                    .writeTimeout(connectTimeout, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .connectionPool(new ConnectionPool(5, 20, TimeUnit.SECONDS))
                    .dispatcher(dispatcher)
                    .sslSocketFactory(sslSocketFactory, x509TrustManager)
                    .build();
            return client;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }
}
