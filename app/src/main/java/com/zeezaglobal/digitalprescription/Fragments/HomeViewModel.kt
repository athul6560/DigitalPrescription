package com.zeezaglobal.digitalprescription.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.DTO.DoctorId
import com.zeezaglobal.digitalprescription.DTO.DoctorResponse
import com.zeezaglobal.digitalprescription.Entity.Patient
import com.zeezaglobal.digitalprescription.Repository.DoctorRepository
import com.zeezaglobal.digitalprescription.Repository.PatientRepository

class HomeViewModel : ViewModel() {
    private val repository = DoctorRepository()
    private val patientRepository = PatientRepository()

    private val _patients = MutableLiveData<List<Patient>>()
    val patients: LiveData<List<Patient>> = _patients

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentPage = 0
    private var totalPages = 1
    private val pageSize = 50

    fun loadPatients(token: String,doctorId: Long) {
        if (currentPage >= totalPages) return // No more pages to load

        _isLoading.postValue(true)

        patientRepository.getPatients(token,doctorId, currentPage, pageSize).observeForever { response ->
            _isLoading.postValue(false)

            response?.let {
                currentPage++
                totalPages = it.totalPages
                val updatedList = _patients.value.orEmpty() + it.content
                _patients.postValue(updatedList)
            }
        }
    }
    fun getDoctor(token: String, doctorId: DoctorId): LiveData<DoctorResponse?> {
        return repository.getDoctor(token, doctorId)
    }
    fun saveNewPatient(token: String, patient: Patient): LiveData<Patient?> {
        return patientRepository.savePatient(token, patient)
    }
}