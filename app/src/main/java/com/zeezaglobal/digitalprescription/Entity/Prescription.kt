package com.zeezaglobal.digitalprescription.Entity
data class Prescription(
    val id: Long,
    val prescribedDate: String,
    val remarks: String,
    val patientId: Long,
    val doctorId: Long,
    val drugs: List<Drug>,
    val drugIds: List<Long>
)

