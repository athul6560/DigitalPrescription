package com.zeezaglobal.digitalprescription.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.zeezaglobal.digitalprescription.Entity.Prescription
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrescriptionRepositoryImpl : PrescriptionRepository {

    companion object {
        private const val TAG = "PrescriptionRepository"
    }

    private val apiService = RetrofitClient.apiService

    private val _prescriptions = MutableLiveData<List<Prescription>>()
    override val prescriptions: LiveData<List<Prescription>> get() = _prescriptions

    override fun getPrescriptionsForPatient(patientId: Long) {
        Log.d(TAG, "Starting fetch for prescriptions of patientId=$patientId")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val prescriptions = apiService.getPrescriptions(patientId)
                val filtered = prescriptions.filter { it.patientId == patientId }
                Log.d(TAG, "Fetched ${filtered.size} prescriptions for patientId=$patientId")
                _prescriptions.postValue(filtered)
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching prescriptions for patientId=$patientId", e)
                _prescriptions.postValue(emptyList())
            }
        }
    }
}