package com.zeezaglobal.digitalprescription.Repository

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zeezaglobal.digitalprescription.DTO.DoctorDetailsDTO
import com.zeezaglobal.digitalprescription.DTO.DoctorId
import com.zeezaglobal.digitalprescription.DTO.DoctorResponse
import com.zeezaglobal.digitalprescription.DTO.RegisterData
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient.apiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorRepository() {
    private val apiService = RetrofitClient.apiService
    fun getDoctor(token: String, doctorId: DoctorId): LiveData<DoctorResponse?> {
        val doctorLiveData = MutableLiveData<DoctorResponse?>()

        apiService.getDoctor("Bearer $token", doctorId.id).enqueue(object : Callback<DoctorResponse> {
            override fun onResponse(call: Call<DoctorResponse>, response: Response<DoctorResponse>) {
                if (response.isSuccessful) {
                    doctorLiveData.postValue(response.body())
                } else {
                    doctorLiveData.postValue(null)
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                doctorLiveData.postValue(null)
            }
        })

        return doctorLiveData
    }

    fun updateDoctor(token: String, doctorDetails: DoctorDetailsDTO): LiveData<DoctorResponse?> {
        val liveData = MutableLiveData<DoctorResponse>()

        val call = apiService.updateDoctor("Bearer $token", doctorDetails)
        call.enqueue(object : Callback<DoctorResponse> {
            override fun onResponse(
                call: Call<DoctorResponse>,
                response: Response<DoctorResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        liveData.postValue(it) // Post the successful DoctorResponse to LiveData
                    }
                } else {
                    // Handle the failure case by posting a failed response or a default response

                    liveData.postValue(null)

                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                // Handle failure case by posting an error message

                liveData.postValue(null)

            }
        })

        return liveData
    }


}