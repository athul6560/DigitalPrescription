package com.zeezaglobal.digitalprescription.RestApi

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
   // private const val BASE_URL = "http://147.93.114.66:9090/"
  // private const val BASE_URL = "http://10.0.0.37:9090/"

     private const val BASE_URL = " https://indigorx.me/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = AuthInterceptor()

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)         // 🔑 Add the token interceptor first
        .addInterceptor(loggingInterceptor)      // 🪵 Logging interceptor last
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}