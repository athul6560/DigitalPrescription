package com.zeezaglobal.digitalprescription.Entity


data class PrescribedDrug(
    val drugName: String,
    val form: String,
    val weight: Double,
    val dosage: String,
    val frequencyPerDay: Int,
    val durationDays: Int,
    val instructions: String
)