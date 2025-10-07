package com.zeezaglobal.digitalprescription.Repository

import com.zeezaglobal.digitalprescription.Entity.Drug
import com.zeezaglobal.digitalprescription.RestApi.ApiService
import com.zeezaglobal.digitalprescription.RestApi.RetrofitClient
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class DrugRepositoryImpl @Inject constructor() : DrugRepository {
    private val drugApi = RetrofitClient.apiService

    override suspend fun searchDrugs(keyword: String): List<Drug> {
        return drugApi.searchDrugs(keyword)
    }
}