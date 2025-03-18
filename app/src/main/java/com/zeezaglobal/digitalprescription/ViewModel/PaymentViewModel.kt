package com.zeezaglobal.digitalprescription.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.DTO.PaymentMethodPayload
import com.zeezaglobal.digitalprescription.DTO.PaymentResponse
import com.zeezaglobal.digitalprescription.Repository.StripeRepository

class PaymentViewModel: ViewModel() {
    private val repository = StripeRepository()
    private val paymentResponseLiveData = MutableLiveData<PaymentResponse>()
    private val errorLiveData = MutableLiveData<String>()

    fun getPaymentResponse(): LiveData<PaymentResponse> = paymentResponseLiveData
    fun getError(): LiveData<String> = errorLiveData
    private val _paymentMethodStatus = MutableLiveData<Result<Unit>>()
    val paymentMethodStatus: LiveData<Result<Unit>> get() = _paymentMethodStatus
    fun attachPaymentMethod(token: String, paymentMethodId: String, customerId: String): LiveData<Boolean> {
        return repository.attachPaymentMethod(token, paymentMethodId, customerId)
    }
    fun createSubscriptionIntent(token: String, customerId: String, isMonthly: Boolean) {
        repository.createPaymentIntent(token,customerId, isMonthly, object : StripeRepository.PaymentIntentCallback {
            override fun onSuccess(paymentResponse: PaymentResponse) {
                paymentResponseLiveData.value = paymentResponse
            }

            override fun onFailure(errorMessage: String) {
                errorLiveData.value = errorMessage
            }
        })
    }
}