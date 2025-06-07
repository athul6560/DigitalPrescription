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
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@HiltViewModel
class PaymentViewModel @Inject constructor(private val stripeRepository: StripeRepository) : ViewModel() {

    private val _clientSecret = MutableLiveData<String>()
    val clientSecret: LiveData<String> = _clientSecret


    fun createSetupIntent( customerId: String) {
        stripeRepository.createStripeSetupIntent( customerId)
    }

    private val _isButtonEnabled = MutableLiveData(false)
    val isButtonEnabled: LiveData<Boolean> get() = _isButtonEnabled

    fun enableButton() {
        _isButtonEnabled.value = true
    }

    fun disableButton() {
        _isButtonEnabled.value = false
    }

    fun toggleButton() {
        _isButtonEnabled.value = !(_isButtonEnabled.value ?: true)
    }
}