package com.zeezaglobal.digitalprescription.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.DTO.PaymentMethodPayload
import com.zeezaglobal.digitalprescription.DTO.PaymentResponse
import com.zeezaglobal.digitalprescription.Repository.StripeRepository
import com.zeezaglobal.digitalprescription.RestApi.ApiService

class PaymentViewModel(private val apiService: ApiService) : ViewModel() {

    private val _clientSecret = MutableLiveData<String>()
    val clientSecret: LiveData<String> get() = _clientSecret

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun createStripeSetupIntent(token: String, customerId: String) {
        val request = SetupIntentRequest(customerId)
        val call = apiService.createSetupIntent("Bearer $token", request)

        call.enqueue(object : Callback<SetupIntentResponse> {
            override fun onResponse(
                call: Call<SetupIntentResponse>,
                response: Response<SetupIntentResponse>
            ) {
                if (response.isSuccessful) {
                    val secret = response.body()?.clientSecret
                    Log.d("Stripe", "Client Secret: $secret")
                    _clientSecret.postValue(secret)
                } else {
                    val errorMessage = "Request failed: ${response.code()}"
                    Log.e("Stripe", errorMessage)
                    _error.postValue(errorMessage)
                }
            }

            override fun onFailure(call: Call<SetupIntentResponse>, t: Throwable) {
                val errorMessage = "Network error: ${t.message}"
                Log.e("Stripe", errorMessage)
                _error.postValue(errorMessage)
            }
        })
    }
}