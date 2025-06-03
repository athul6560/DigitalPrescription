package com.zeezaglobal.digitalprescription.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.DTO.PaymentIntentRequest
import com.zeezaglobal.digitalprescription.DTO.PaymentMethodPayload
import com.zeezaglobal.digitalprescription.DTO.PaymentResponse
import com.zeezaglobal.digitalprescription.DTO.SetupIntentRequest
import com.zeezaglobal.digitalprescription.DTO.SetupIntentResponse
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StripeRepository {
    private val apiService = RetrofitClient.apiService


    fun createStripeSetupIntent(customerId: String) {
        val request = SetupIntentRequest(customerId)
        val call = apiService.createSetupIntent(request)

        call.enqueue(object : Callback<SetupIntentResponse> {
            override fun onResponse(
                call: Call<SetupIntentResponse>,
                response: Response<SetupIntentResponse>
            ) {
                if (response.isSuccessful) {
                    val clientSecret = response.body()?.clientSecret
                    Log.d("Stripe", "Client Secret: $clientSecret")
                } else {
                    Log.e("Stripe", "Request failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SetupIntentResponse>, t: Throwable) {
                Log.e("Stripe", "Network error: ${t.message}")
            }
        })
    }

}