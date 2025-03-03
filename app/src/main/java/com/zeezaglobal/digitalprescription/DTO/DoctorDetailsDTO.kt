package com.zeezaglobal.digitalprescription.DTO

data class DoctorDetailsDTO (
    val id: Int,
    val firstName: String,
    val lastName: String,
    val specialization: String,
    val licenseNumber: String,
    val hospitalName: String,
    val contactNumber: String
)
data class DoctorResponse(
    val id: Int,
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val specialization: String,
    val licenseNumber: String,
    val hospitalName: String,
    val contactNumber: String
)