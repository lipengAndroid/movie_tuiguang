package com.judian.watch.videos.Http;

/*
 * Created by Administrator on 2017/10/19 0019.
 * <p>
 * 默认信任所有的证书
 * TODO 最好加上证书认证，主流App都有自己的证书
 *
 * @return 默认信任所有的证书
 * TODO 最好加上证书认证，主流App都有自己的证书
 * @return 默认信任所有的证书
 * TODO 最好加上证书认证，主流App都有自己的证书
 * @return
 */

/*
 * 默认信任所有的证书
 * TODO 最好加上证书认证，主流App都有自己的证书
 *
 * @return
 */

import android.annotation.SuppressLint;

import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

public class TrustAllCerts implements X509TrustManager {
    @SuppressLint("TrustAllX509TrustManager")
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    @SuppressLint("TrustAllX509TrustManager")
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}