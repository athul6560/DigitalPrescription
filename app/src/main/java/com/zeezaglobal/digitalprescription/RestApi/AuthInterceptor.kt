package com.zeezaglobal.digitalprescription.RestApi

import com.zeezaglobal.digitalprescription.SharedPreference.TokenManager
import okhttp3.Interceptor
import okhttp3.Response



class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        val requestUrl = originalRequest.url.encodedPath

        // Skip adding token for these endpoints
        if (!requestUrl.contains("/auth/login") && !requestUrl.contains("/auth/register")) {
            val token = TokenManager.getToken()
            if (!token.isNullOrEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}