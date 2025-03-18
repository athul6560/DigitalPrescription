package com.zeezaglobal.digitalprescription.DTO

data class PaymentResponse(
    val clientSecret: String,
    val subscriptionId: String,
    val ephemeralKey: String
)