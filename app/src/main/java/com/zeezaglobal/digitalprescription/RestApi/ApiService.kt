package com.zeezaglobal.digitalprescription.RestApi

import com.zeezaglobal.digitalprescription.DTO.DoctorDetailsDTO
import com.zeezaglobal.digitalprescription.DTO.DoctorResponse
import com.zeezaglobal.digitalprescription.DTO.LoginData
import com.zeezaglobal.digitalprescription.DTO.LoginResponse
import com.zeezaglobal.digitalprescription.DTO.PaginatedResponse
import com.zeezaglobal.digitalprescription.DTO.PaymentIntentRequest
import com.zeezaglobal.digitalprescription.DTO.PaymentMethodPayload
import com.zeezaglobal.digitalprescription.DTO.PaymentResponse
import com.zeezaglobal.digitalprescription.DTO.PostApiResponse
import com.zeezaglobal.digitalprescription.DTO.RegisterData
import com.zeezaglobal.digitalprescription.DTO.SetupIntentRequest
import com.zeezaglobal.digitalprescription.DTO.SetupIntentResponse
import com.zeezaglobal.digitalprescription.Entity.Patient
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @POST("auth/login")
    fun login( @Body loginData: LoginData): Call<LoginResponse>

    @POST("auth/register")
    fun register( @Body registerData: RegisterData): Call<PostApiResponse>

    @GET("doctor/getdata/{doctorId}")
    fun getDoctor( @Path("doctorId") doctorId: Long): Call<DoctorResponse>

    @POST("doctor/update")
    fun updateDoctor(@Body doctorDetails: DoctorDetailsDTO): Call<DoctorResponse>

    @POST("api/patients")
    fun addPatient( @Body doctorDetails: Patient): Call<Patient>

    @GET("api/patients/search")
    fun searchPatients(
        @Query("firstName") firstName: String
    ): Call<List<Patient>>

    @GET("api/patients/doctor/{doctorId}")
    fun getPatients(

        @Path("doctorId") doctorId: Long,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String = "firstName,asc"
    ): Call<PaginatedResponse<Patient>>



    @POST("api/stripe/setup-intent")
    @Headers("Content-Type: application/json")
    fun createSetupIntent(@Body request: SetupIntentRequest): Call<SetupIntentResponse>



}