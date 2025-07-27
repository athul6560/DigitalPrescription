package com.zeezaglobal.digitalprescription.Entity
data class Drug(
    val id: Long,
    val serialNumber: Int,
    val type: String,
    val name: String,
    val description: String,
    val type_name: String,
    val form: String
)