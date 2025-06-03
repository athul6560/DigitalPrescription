package com.zeezaglobal.digitalprescription.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.DTO.PaginatedResponse
import com.zeezaglobal.digitalprescription.DTO.PostApiResponse
import com.zeezaglobal.digitalprescription.DTO.RegisterData
import com.zeezaglobal.digitalprescription.Entity.Patient
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PatientRepository {
    private val apiService = RetrofitClient.apiService
    fun getPatients(doctorId: Long, page: Int, size: Int): LiveData<PaginatedResponse<Patient>?> {
        val data = MutableLiveData<PaginatedResponse<Patient>?>()

        apiService.getPatients(doctorId, page, size).enqueue(object : Callback<PaginatedResponse<Patient>> {
            override fun onResponse(call: Call<PaginatedResponse<Patient>>, response: Response<PaginatedResponse<Patient>>) {
                if (response.isSuccessful) {
                    data.postValue(response.body())
                } else {
                    data.postValue(null) // Handle error case
                }
            }

            override fun onFailure(call: Call<PaginatedResponse<Patient>>, t: Throwable) {
                data.postValue(null) // Handle failure case
            }
        })

        return data
    }
     fun searchPatient( firstName: String): LiveData<List<Patient>> {
        val listOfPatients = MutableLiveData<List<Patient>>()

        apiService.searchPatients(firstName)
            .enqueue(object : Callback<List<Patient>> {
                override fun onResponse(call: Call<List<Patient>>, response: Response<List<Patient>>) {
                    if (response.isSuccessful) {
                        response.body()?.let { patients ->
                            listOfPatients.postValue(patients) // Update LiveData with the patient list
                            for (patient in patients) {
                                Log.d("PatientData", "Name: ${patient.firstName} ${patient.lastName}, Contact: ${patient.contactNumber}")
                            }
                        } ?: run {
                            listOfPatients.postValue(emptyList()) // Post empty list if no patients found
                            Log.d("PatientData", "No patients found")
                        }
                    } else {
                        listOfPatients.postValue(emptyList()) // Post empty list in case of error
                        Log.e("PatientData", "Request failed: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<List<Patient>>, t: Throwable) {
                    listOfPatients.postValue(emptyList()) // Post empty list in case of network failure
                    Log.e("PatientData", "Error: ${t.message}")
                }
            })

        return listOfPatients
    }

    fun savePatient( patient: Patient): LiveData<Patient?> {
        val liveData = MutableLiveData<Patient?>()

        apiService.addPatient( patient).enqueue(object : Callback<Patient> {
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