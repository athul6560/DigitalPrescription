package com.zeezaglobal.digitalprescription.Fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.DTO.DoctorId
import com.zeezaglobal.digitalprescription.DTO.DoctorResponse
import com.zeezaglobal.digitalprescription.Entity.Patient
import com.zeezaglobal.digitalprescription.Repository.DoctorRepository
import com.zeezaglobal.digitalprescription.Repository.PatientRepository

class HomeViewModel : ViewModel() {
    private val repository = DoctorRepository()
    private val patientRepository = PatientRepository()
    fun getDoctor(token: String, doctorId: DoctorId): LiveData<DoctorResponse?> {
        return repository.getDoctor(token, doctorId)
    }
    fun saveNewPatient(token: String, patient: Patient): LiveData<Patient?> {
        return patientRepository.savePatient(token, patient)
    }
}