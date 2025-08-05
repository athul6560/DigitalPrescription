package com.zeezaglobal.digitalprescription.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.DTO.PrescriptionResponseDTO
import com.zeezaglobal.digitalprescription.Entity.PrescribedDrug
import kotlin.collections.map
import com.zeezaglobal.digitalprescription.Entity.Prescription
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PrescriptionRepositoryImpl : PrescriptionRepository {



    private val apiService = RetrofitClient.apiService

    private val _prescriptions = MutableLiveData<List<Prescription>>()
    override val prescriptions: LiveData<List<Prescription>> get() = _prescriptions
    override fun getPrescriptionsForPatient(patientId: Long, doctorId: Long) {


        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getPrescriptionsByDoctorAndPatient(doctorId, patientId).execute()
                if (response.isSuccessful) {
                    val dtoList = response.body() ?: emptyList()
                    val mapped = dtoList.map { mapDtoToEntity(it) }
                    _prescriptions.postValue(mapped)
                } else {
                    Log.e("PrescriptionRepo", "API call failed: ${response.code()}")
                    _prescriptions.postValue(emptyList())
                }
            } catch (e: Exception) {
                Log.e("PrescriptionRepo", "Error fetching prescriptions", e)
                _prescriptions.postValue(emptyList())
            }
        }
    }

    override fun mapDtoToEntity(dto: PrescriptionResponseDTO): Prescription {
        return Prescription(
            id = dto.id,
            remarks = dto.remarks,
            prescribedDate = dto.prescribedDate,
            doctorName = dto.doctorName,
            patientName = dto.patientName,
            prescribedDrugs = dto.prescribedDrugs.map {
                PrescribedDrug(
                    drugName = it.drugName,
                    form = it.form,
                    weight = it.weight,
                    dosage = it.dosage,
                    frequencyPerDay = it.frequencyPerDay,
                    durationDays = it.durationDays,
                    instructions = it.instructions
                )
            }
        )
    }
}