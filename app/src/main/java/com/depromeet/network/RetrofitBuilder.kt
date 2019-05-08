package com.depromeet.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private const val BASE_URL = "http://3.16.157.244:8080/"
    private const val TIME_OUT_SEC = 5L
    private var service: ServiceApi? = null

    fun getInstance(): ServiceApi {
        return service ?: initService()
    }

    private fun initService(): ServiceApi {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        builder.connectTimeout(TIME_OUT_SEC, TimeUnit.SECONDS)
        val okHttpClient = builder.build()

        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ServiceApi::class.java)
    }
}