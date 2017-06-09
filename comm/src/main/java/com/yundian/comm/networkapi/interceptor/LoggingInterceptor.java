package com.yundian.comm.networkapi.interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yaowang on 2017/4/20.
 */

public class LoggingInterceptor implements Interceptor {

    @Override public Response intercept(Interceptor.Chain chain) throws IOException {

        Request request = chain.request();
        Log.e(this.getClass().getSimpleName(),String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
        if( request.body() != null && request.body().contentLength() > 0 ) {
            Log.e(this.getClass().getSimpleName(), String.format("Sending body %s", request.body().toString()));
        }
        long t1 = System.nanoTime();
        okhttp3.Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();
        Log.e(this.getClass().getSimpleName(),String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.e(this.getClass().getSimpleName(),content);
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
