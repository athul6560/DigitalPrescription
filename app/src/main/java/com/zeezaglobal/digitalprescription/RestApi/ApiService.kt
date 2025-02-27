package com.zeezaglobal.digitalprescription.RestApi

import com.zeezaglobal.digitalprescription.Entity.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users/{id}")
    fun getUser(@Path("id") userId: Int): Call<User>
}