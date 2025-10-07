package com.zeezaglobal.digitalprescription.Repository

import com.zeezaglobal.digitalprescription.Entity.Drug

interface DrugRepository {
    suspend fun searchDrugs(keyword: String): List<Drug>
}