package com.zeezaglobal.digitalprescription.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.DTO.PaymentIntentRequest
import com.zeezaglobal.digitalprescription.DTO.PaymentMethodPayload
import com.zeezaglobal.digitalprescription.DTO.PaymentResponse
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StripeRepository {
    private val apiService = RetrofitClient.apiService
    fun attachPaymentMethod(token: String, paymentMethodId: String, customerId: String): LiveData<Boolean> {
        val liveData = MutableLiveData<Boolean>()
        val payload = PaymentMethodPayload(
            paymentMethodId = paymentMethodId,
            customerId = customerId
        )

        apiService.attachPaymentMethod("Bearer $token", payload).enqueue(object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                liveData.postValue(response.isSuccessful)
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                liveData.postValue(false) // Handle network failure case
            }
        })

        return liveData
    }
    fun createPaymentIntent(token: String,customerId: String, isMonthly: Boolean, callback: PaymentIntentCallback) {
        val paymentRequest = PaymentIntentRequest(customerId, isMonthly)
        val call = apiService.createSubscription("Bearer $token",paymentRequest)

        call.enqueue(object : retrofit2.Callback<PaymentResponse> {
            override fun onResponse(call: Call<PaymentResponse>, response: retrofit2.Response<PaymentResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    callback.onSuccess(response.body()!!)
                } else {
                    callback.onFailure(response.message())
                }
            }

            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                callback.onFailure(t.message ?: "Unknown error")
            }
        })
    }

    interface PaymentIntentCallback {
        fun onSuccess(paymentResponse: PaymentResponse)
        fun onFailure(errorMessage: String)
    }
}