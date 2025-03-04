package com.zeezaglobal.digitalprescription.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.DTO.DoctorId
import com.zeezaglobal.digitalprescription.DTO.DoctorResponse
import com.zeezaglobal.digitalprescription.Repository.DoctorRepository

class HomeViewModel : ViewModel() {
    private val repository = DoctorRepository()
    fun getDoctor(token: String, doctorId: DoctorId): LiveData<DoctorResponse?> {
        return repository.getDoctor(token, doctorId)
    }
}