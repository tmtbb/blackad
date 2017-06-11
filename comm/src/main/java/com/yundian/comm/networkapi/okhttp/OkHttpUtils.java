package com.yundian.comm.networkapi.okhttp;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.yundian.comm.networkapi.interceptor.LoggingInterceptor;
import com.yundian.comm.networkapi.config.NetworkAPIConfig;
import com.yundian.comm.networkapi.interceptor.SignInterceptor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by yaowang on 2017/5/3.
 */

public class OkHttpUtils {
    private static OkHttpUtils okHttpUtils = new OkHttpUtils();

    private OkHttpUtils() {

    }

    public static OkHttpUtils getInstance() {
        return okHttpUtils;
    }



    private Retrofit retrofit;
    private OkHttpClient okHttpClient;
    private NetworkAPIConfig config;

    public void initConfig(NetworkAPIConfig config) {
        this.config = config;
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        builder.connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(config.getWriteTimeout(), TimeUnit.SECONDS);
        builder.readTimeout(config.getReadTimeout(), TimeUnit.SECONDS);
        SignInterceptor signInterceptor = new SignInterceptor(config.getSignCalculate(), config.getCommParameter());
        builder.addInterceptor(signInterceptor);
//        builder.addInterceptor(new LoggingInterceptor());
        okHttpClient = builder.build();
        createRetrofit();
    }

    public static OkHttpUtils getOkHttpUtils() {
        return okHttpUtils;
    }

    public NetworkAPIConfig getConfig() {
        return config;
    }

    private void createRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(config.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public void setCertificates(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }

            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            okHttpClient = okHttpClient.newBuilder().sslSocketFactory(sslContext.getSocketFactory()).build();
            createRetrofit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }


}
