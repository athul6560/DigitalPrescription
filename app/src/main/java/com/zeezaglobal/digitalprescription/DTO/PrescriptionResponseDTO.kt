package com.zeezaglobal.digitalprescription.DTO

data class PrescriptionResponseDTO(
    val id: Long,
    val remarks: String,
    val prescribedDate: String,
    val doctorName: String,
    val patientName: String,
    val prescribedDrugs: List<PrescribedDrugDTO>
)

data class PrescribedDrugDTO(
    val drugName: String,
    val form: String,
    val weight: Double,
    val dosage: String,
    val frequencyPerDay: Int,
    val durationDays: Int,
    val instructions: String
)

data class DrugDTO(
    val id: Long,
    val name: String,
    val description: String,
    val type: String,
    val form: String
)