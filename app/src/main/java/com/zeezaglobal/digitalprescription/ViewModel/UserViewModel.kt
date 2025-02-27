package com.zeezaglobal.digitalprescription.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.Entity.User
import com.zeezaglobal.digitalprescription.Repository.UserRepository

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    fun getUser(userId: Int): LiveData<User?> {
        return repository.getUser(userId)
    }
}