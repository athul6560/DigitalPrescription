package com.zeezaglobal.digitalprescription.RestApi

import com.zeezaglobal.digitalprescription.Activities.DoctorDeatilsActivity
import com.zeezaglobal.digitalprescription.DTO.DoctorDetailsDTO
import com.zeezaglobal.digitalprescription.DTO.DoctorId
import com.zeezaglobal.digitalprescription.DTO.DoctorResponse
import com.zeezaglobal.digitalprescription.DTO.LoginData
import com.zeezaglobal.digitalprescription.DTO.LoginResponse
import com.zeezaglobal.digitalprescription.DTO.PaginatedResponse
import com.zeezaglobal.digitalprescription.DTO.PostApiResponse
import com.zeezaglobal.digitalprescription.DTO.RegisterData
import com.zeezaglobal.digitalprescription.Entity.Patient
import com.zeezaglobal.digitalprescription.Entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header

import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @POST("auth/login")
    fun login( @Body loginData: LoginData): Call<LoginResponse>

    @POST("auth/register")
    fun register( @Body registerData: RegisterData): Call<PostApiResponse>

    @GET("doctor/getdata/{doctorId}")
    fun getDoctor(@Header("Authorization") token: String,  @Path("doctorId") doctorId: Long): Call<DoctorResponse>

    @POST("doctor/update")
    fun updateDoctor(@Header("Authorization") token: String, @Body doctorDetails: DoctorDetailsDTO): Call<DoctorResponse>

    @POST("api/patients")
    fun addPatient(@Header("Authorization") token: String, @Body doctorDetails: Patient): Call<Patient>

    @GET("api/patients/search")
    fun searchPatients(@Header("Authorization") token: String,
        @Query("firstName") firstName: String
    ): Call<List<Patient>>

    @GET("api/patients/doctor/{doctorId}")
    fun getPatients(
        @Header("Authorization") token: String,
        @Path("doctorId") doctorId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String = "firstName,asc"
    ): Call<PaginatedResponse<Patient>>
}