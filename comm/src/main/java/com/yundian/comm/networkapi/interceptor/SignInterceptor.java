package com.yundian.comm.networkapi.interceptor;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.yundian.comm.networkapi.sign.SignCalculate;
import com.yundian.comm.util.StringUtils;

/**
 * Created by yaowang on 2017/5/3.
 */

public class SignInterceptor implements Interceptor {

    private Map<String,Object> commParameter;
    private SignCalculate signCalculate;

    public SignInterceptor(SignCalculate signCalculate, Map<String, Object> commParameter) {
        this.commParameter = commParameter;
        this.signCalculate = signCalculate;
    }

    private TreeMap<String, Object> getSrcParam(Request oldRequest) {
        TreeMap<String, Object> map = new TreeMap<String, Object>();
        RequestBody requestBody = oldRequest.body();
        if( requestBody instanceof FormBody) {
            FormBody formBody = (FormBody) requestBody;
            for(int i = 0 ; i < formBody.size() ; ++i ) {
                map.put(formBody.name(i), formBody.value(i));
            }
        }
        else {
            HttpUrl httpUrl = oldRequest.url();
            Set<String> names = httpUrl.queryParameterNames();
            for (String name : names) {
                map.put(name, httpUrl.queryParameter(name));
            }
        }
        return map;
    }

    private Request newReqeust(Request oldRequest) {
        HttpUrl httpUrl = oldRequest.url();
        TreeMap<String, Object> map = getSrcParam(oldRequest);
        map.put("timestamp", new Date().getTime() / 1000 + "");
        map.put("nonceStr", StringUtils.randomUUID());
        Object token = map.get("token");
        map.putAll(commParameter);
        if( token != null ) {
            map.put("token",token);
        }


        String url = httpUrl.url().getProtocol() + "://" + httpUrl.url().getHost();
        if (httpUrl.port() != 80 && httpUrl.port() != 443 ) {
            url += ":" + httpUrl.url().getPort();
        }
        url += httpUrl.url().getPath();

        if( signCalculate != null ) {
            String sign = signCalculate.calculate(oldRequest.method(), url, map);
            map.put(signCalculate.parameterKey(), sign);
        }

        RequestBody requestBody = oldRequest.body();
        HttpUrl.Builder builder = oldRequest.url().newBuilder();
        if( oldRequest.method().equals("POST") ) {
            FormBody.Builder builder1 = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder1.add(entry.getKey(), entry.getValue().toString());
            }
            requestBody = builder1.build();
        }
        else {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder.setEncodedQueryParameter(entry.getKey(), entry.getValue().toString());
            }
        }


        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(),requestBody)
                .url(builder.build())
                .build();

        return newRequest;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = newReqeust(request);
        Response response = chain.proceed(request);
        return response;
    }
}
