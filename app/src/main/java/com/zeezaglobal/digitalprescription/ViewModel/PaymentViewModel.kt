package com.zeezaglobal.digitalprescription.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.DTO.PaymentMethodPayload
import com.zeezaglobal.digitalprescription.DTO.PaymentResponse
import com.zeezaglobal.digitalprescription.DTO.SetupIntentRequest
import com.zeezaglobal.digitalprescription.DTO.SetupIntentResponse
import com.zeezaglobal.digitalprescription.Repository.StripeRepository
import com.zeezaglobal.digitalprescription.RestApi.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PaymentViewModel(private val stripeRepository: StripeRepository) : ViewModel() {

    private val _clientSecret = MutableLiveData<String>()
    val clientSecret: LiveData<String> = _clientSecret


    fun createSetupIntent( customerId: String) {
        stripeRepository.createStripeSetupIntent( customerId)
    }
}