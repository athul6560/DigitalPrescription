package com.zeezaglobal.digitalprescription.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.DTO.DoctorDetailsDTO
import com.zeezaglobal.digitalprescription.DTO.DoctorResponse
import com.zeezaglobal.digitalprescription.Repository.DoctorRepository
import com.zeezaglobal.digitalprescription.Repository.UserRepository


class DoctorViewModel() : ViewModel() {
    private val repository = DoctorRepository()

    fun updateDoctor(token:String, doctorDetails: DoctorDetailsDTO): LiveData<DoctorResponse?> {
        return repository.updateDoctor(token, doctorDetails)
    }
}
