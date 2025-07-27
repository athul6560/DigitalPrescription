package com.zeezaglobal.digitalprescription.Entity

data class Patient(
    val numberOfVisit: Int,
    val id: Long =0,
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val gender: String,
    val contactNumber: String,
    val email: String,
    val address: String,
    val medicalHistory: String,
    val doctor: Doctor
)

