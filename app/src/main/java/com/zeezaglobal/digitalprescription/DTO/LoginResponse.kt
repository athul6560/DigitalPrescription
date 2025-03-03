package com.zeezaglobal.digitalprescription.DTO

import com.zeezaglobal.digitalprescription.Entity.User

data class LoginResponse(

    val user: User,
    val token: String

)