package com.zeezaglobal.digitalprescription.RestApi

import com.zeezaglobal.digitalprescription.DTO.LoginData
import com.zeezaglobal.digitalprescription.DTO.LoginResponse
import com.zeezaglobal.digitalprescription.DTO.PostApiResponse
import com.zeezaglobal.digitalprescription.DTO.RegisterData
import com.zeezaglobal.digitalprescription.Entity.User
import retrofit2.Call
import retrofit2.http.Body

import retrofit2.http.POST


interface ApiService {
    @POST("auth/login")
    fun login( @Body loginData: LoginData): Call<LoginResponse>

    @POST("auth/register")
    fun register( @Body registerData: RegisterData): Call<PostApiResponse>
}