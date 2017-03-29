package com.bignerdranch.stockwatcher.model.service

import com.google.common.net.HttpHeaders.CONTENT_TYPE
import okhttp3.Interceptor
import okhttp3.Response

class ContentTypeHeaderInterceptor : Interceptor {

    private val APPLICATION_JSON = "application/json"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
                .request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build()

        return chain.proceed(request)
    }
}
