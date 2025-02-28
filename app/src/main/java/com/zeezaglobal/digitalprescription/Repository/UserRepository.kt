package com.zeezaglobal.digitalprescription.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.DTO.LoginData
import com.zeezaglobal.digitalprescription.DTO.LoginResponse
import com.zeezaglobal.digitalprescription.Entity.User
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val apiService = RetrofitClient.apiService

    fun login(email: String, password: String): LiveData<String?> {
        val token = MutableLiveData<String?>()
        val user = LoginData(email, password)

        apiService.login(user).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    token.postValue(responseBody?.token) // Store the token from the response
                } else {
                    token.postValue(null) // API returned an error response
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                token.postValue(null) // Handle network failure
            }
        })

        return token
    }
}
