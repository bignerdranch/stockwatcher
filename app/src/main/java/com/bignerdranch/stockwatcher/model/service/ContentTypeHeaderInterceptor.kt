package com.bignerdranch.stockwatcher.model.service

import com.google.common.net.HttpHeaders.CONTENT_TYPE
import okhttp3.Interceptor
import okhttp3.Response

class ContentTypeHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
                .request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, "application/json")
                .build()

        return chain.proceed(request)
    }
}
