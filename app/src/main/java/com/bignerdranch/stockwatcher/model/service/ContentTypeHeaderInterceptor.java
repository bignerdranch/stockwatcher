package com.bignerdranch.stockwatcher.model.service;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class ContentTypeHeaderInterceptor implements Interceptor {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain
                .request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        return chain.proceed(request);
    }
}
