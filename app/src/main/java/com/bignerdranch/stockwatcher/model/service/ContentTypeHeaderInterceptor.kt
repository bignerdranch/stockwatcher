package com.bignerdranch.stockwatcher.model.service

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ContentTypeHeaderInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
                .request()
                .newBuilder()
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build()

        return chain.proceed(request)
    }

    companion object {

        private val CONTENT_TYPE = "Content-Type"
        private val APPLICATION_JSON = "application/json"
    }
}
