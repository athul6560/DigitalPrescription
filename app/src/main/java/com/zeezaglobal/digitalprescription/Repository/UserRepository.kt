package com.zeezaglobal.digitalprescription.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.DTO.LoginData
import com.zeezaglobal.digitalprescription.DTO.LoginResponse
import com.zeezaglobal.digitalprescription.DTO.PostApiResponse
import com.zeezaglobal.digitalprescription.DTO.RegisterData
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import com.zeezaglobal.digitalprescription.SharedPreference.TokenManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val apiService = RetrofitClient.apiService
    fun register(email: String, password: String): LiveData<PostApiResponse?> {
        val liveData = MutableLiveData<PostApiResponse?>()
        val user = RegisterData(password, email)

        apiService.register(user).enqueue(object : Callback<PostApiResponse> {
            override fun onResponse(call: Call<PostApiResponse>, response: Response<PostApiResponse>) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body())
                } else {
                    liveData.postValue(null) // Handle failure case
                }
            }

            override fun onFailure(call: Call<PostApiResponse>, t: Throwable) {
                liveData.postValue(null) // Handle network failure case
            }
        })

        return liveData
    }
    fun login(email: String, password: String): LiveData<LoginResponse?> {
        val tokenData = MutableLiveData<LoginResponse?>()
        val user = LoginData(email, password)

        apiService.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    tokenData.postValue(responseBody)
                    if (responseBody != null) {
                        TokenManager.setToken(responseBody.token)
                    }
                // Store the token from the response
                } else {
                    tokenData.postValue(null) // API returned an error response
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                tokenData.postValue(null) // Handle network failure
            }
        })

        return tokenData
    }
}
