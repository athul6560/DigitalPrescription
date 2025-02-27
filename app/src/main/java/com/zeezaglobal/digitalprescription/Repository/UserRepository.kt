package com.zeezaglobal.digitalprescription.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.Entity.User
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository {
    private val apiService = RetrofitClient.apiService

    fun getUser(userId: Int): LiveData<User?> {
        val userLiveData = MutableLiveData<User?>()

        apiService.getUser(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    userLiveData.postValue(response.body())
                } else {
                    userLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                userLiveData.postValue(null)
            }
        })

        return userLiveData
    }
}