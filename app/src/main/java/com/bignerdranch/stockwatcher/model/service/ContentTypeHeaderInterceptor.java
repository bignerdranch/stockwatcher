package com.bignerdranch.stockwatcher.model.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

public class ContentTypeHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain
                .request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, "application/json")
                .build();

        return chain.proceed(request);
    }
}
