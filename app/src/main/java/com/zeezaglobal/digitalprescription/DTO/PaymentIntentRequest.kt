package com.zeezaglobal.digitalprescription.DTO

data class PaymentIntentRequest(
    val customerId: String,
    val isMonthly: Boolean
)