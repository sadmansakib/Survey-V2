package com.example.survey.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class TimestampHeaderInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.request().let { request ->
            request.newBuilder().apply {
                header("Date", Calendar.getInstance().toString())
            }
        }.build()

        return chain.proceed(response)
    }
}