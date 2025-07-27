package com.zeezaglobal.digitalprescription.Repository

import androidx.lifecycle.LiveData
import com.zeezaglobal.digitalprescription.Entity.Prescription

interface PrescriptionRepository {
    val prescriptions: LiveData<List<Prescription>>
    fun getPrescriptionsForPatient(patientId: Long)
}