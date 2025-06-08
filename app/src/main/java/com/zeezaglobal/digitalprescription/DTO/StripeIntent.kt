package com.zeezaglobal.digitalprescription.DTO

data class SetupIntentRequest(
    val doctorId: String
)

data class SetupIntentResponse(
    val clientSecret: String
)