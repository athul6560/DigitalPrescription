package com.zeezaglobal.digitalprescription.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zeezaglobal.digitalprescription.Entity.Prescription
import com.zeezaglobal.digitalprescription.Repository.PrescriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class PrescriptionViewModel @Inject constructor(
    private val repository: PrescriptionRepository
) : ViewModel() {

    val prescriptions: LiveData<List<Prescription>> = repository.prescriptions.apply {
        observeForever { list ->
            Log.d("PrescriptionViewModel", "Prescriptions updated, count: ${list.size}")
        }
    }

    fun fetchPrescriptionsForPatient(patientId: Long) {
        Log.d("PrescriptionViewModel", "Fetching prescriptions for patientId=$patientId")
        repository.getPrescriptionsForPatient(patientId)
    }
}