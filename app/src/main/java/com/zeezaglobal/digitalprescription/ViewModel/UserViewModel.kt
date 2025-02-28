package com.zeezaglobal.digitalprescription.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.DTO.PostApiResponse
import com.zeezaglobal.digitalprescription.Entity.User
import com.zeezaglobal.digitalprescription.Repository.UserRepository

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    fun login(email: String, password: String): LiveData<String?> {
        return repository.login(email, password)
    }
    fun register(email: String, password: String,username: String): LiveData<PostApiResponse?> {
        return repository.register(email, password,username)
    }
}