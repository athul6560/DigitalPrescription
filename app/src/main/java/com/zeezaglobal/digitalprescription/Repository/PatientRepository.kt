package com.zeezaglobal.digitalprescription.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.DTO.PostApiResponse
import com.zeezaglobal.digitalprescription.DTO.RegisterData
import com.zeezaglobal.digitalprescription.Entity.Patient
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientRepository {
    private val apiService = RetrofitClient.apiService
    fun savePatient(token: String, patient: Patient): LiveData<Patient?> {
        val liveData = MutableLiveData<Patient?>()

        apiService.addPatient("Bearer $token", patient).enqueue(object : Callback<Patient> {
            override fun onResponse(call: Call<Patient>, response: Response<Patient>) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body())
                } else {
                    liveData.postValue(null) // Handle failure case
                }
            }

            override fun onFailure(call: Call<Patient>, t: Throwable) {
                liveData.postValue(null) // Handle network failure case
            }
        })

        return liveData
    }
}