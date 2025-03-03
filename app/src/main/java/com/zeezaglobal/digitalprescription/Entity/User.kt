package com.zeezaglobal.digitalprescription.Entity

data class User(
    val id: Int,
    val username: String,
    val roles: List<String>
)