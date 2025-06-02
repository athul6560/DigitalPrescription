package com.zeezaglobal.digitalprescription.DTO

data class SetupIntentRequest(
    val customerId: String
)

data class SetupIntentResponse(
    val clientSecret: String
)