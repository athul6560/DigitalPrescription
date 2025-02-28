package com.zeezaglobal.digitalprescription.Entity

data class User(

    val email: String,
    val password: String,
    val username: String,
    val token: String? = null
)