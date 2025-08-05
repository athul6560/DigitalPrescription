package com.zeezaglobal.digitalprescription.DTO

data class PrescriptionRequestDTO(
    val prescribedDate: String, // Format: "2025-08-01"
    val remarks: String,
    val patientId: Long,
    val doctorId: Long,
    val prescribedDrugs: List<PrescribedDrugRequestDTO>
)

data class PrescribedDrugRequestDTO(
    val drugId: Long,
    val weight: Double,
    val dosage: String,
    val frequencyPerDay: Int,
    val durationDays: Int,
    val instructions: String
)