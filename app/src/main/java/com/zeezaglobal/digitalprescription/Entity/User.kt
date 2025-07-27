package com.zeezaglobal.digitalprescription.Entity

data class User(
    val id: Int,
    val username: String,
    val isValidated:Int,
    val roles: List<String>
)