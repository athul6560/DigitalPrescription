package com.zeezaglobal.digitalprescription.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeezaglobal.digitalprescription.DTO.LoginResponse
import com.zeezaglobal.digitalprescription.DTO.PostApiResponse
import com.zeezaglobal.digitalprescription.Entity.User
import com.zeezaglobal.digitalprescription.Repository.UserRepository
import com.zeezaglobal.digitalprescription.Utils.InputValidator
import com.zeezaglobal.digitalprescription.Validation.RegisterErrors
import com.zeezaglobal.digitalprescription.Validation.SucessMessage
import kotlinx.coroutines.launch
import retrofit2.Callback

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _buttonStatus = MutableLiveData<Boolean?>()
    val buttonStatus: LiveData<Boolean?> = _buttonStatus


    fun login(email: String, password: String): LiveData<LoginResponse?> {
        return repository.login(email, password)
    }


    fun register(email: String, password: String, confirmPassword: String): LiveData<PostApiResponse?> {

        val result = InputValidator.validate(email, password, confirmPassword)
        val responseLiveData = MutableLiveData<PostApiResponse?>()
        responseLiveData.postValue(PostApiResponse(false, "Loading"))
        if (result == SucessMessage.OK) {
            _buttonStatus.postValue(false)
            repository.register(email, password).observeForever { apiResponse ->

                responseLiveData.postValue(
                    apiResponse ?: PostApiResponse(false, "Unknown error occurred")
                )
                _buttonStatus.postValue(true)
            }
        } else {
            responseLiveData.postValue(PostApiResponse(false, result))
            _buttonStatus.postValue(true)
        }

        return responseLiveData
    }
}