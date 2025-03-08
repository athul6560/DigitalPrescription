package com.zeezaglobal.digitalprescription.DTO

data class PaginatedResponse<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int
)