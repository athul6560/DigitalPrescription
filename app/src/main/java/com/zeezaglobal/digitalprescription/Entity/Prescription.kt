package com.zeezaglobal.digitalprescription.Entity
data class Prescription(
    val id: Int,
    val patientName: String,
    val doctorName: String,
    val createdDate: String, // ISO date string (e.g. "2025-07-12"), or use LocalDate
    val medicines: List<Drug> = emptyList() // default constructor without medicines

)

