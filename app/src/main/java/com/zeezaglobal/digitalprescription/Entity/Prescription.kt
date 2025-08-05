package com.zeezaglobal.digitalprescription.Entity
data class Prescription(
    val id: Long,
    val remarks: String,
    val prescribedDate: String,
    val doctorName: String,
    val patientName: String,
    val prescribedDrugs: List<PrescribedDrug>
)
