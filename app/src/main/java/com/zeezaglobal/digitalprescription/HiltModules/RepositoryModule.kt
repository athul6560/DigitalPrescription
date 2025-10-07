package com.zeezaglobal.digitalprescription.HiltModules

import com.zeezaglobal.digitalprescription.Repository.DrugRepository
import com.zeezaglobal.digitalprescription.Repository.DrugRepositoryImpl
import com.zeezaglobal.digitalprescription.Repository.PrescriptionRepository
import com.zeezaglobal.digitalprescription.Repository.PrescriptionRepositoryImpl
import com.zeezaglobal.digitalprescription.Repository.StripeRepository
import com.zeezaglobal.digitalprescription.RestApi.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides

    fun provideStripeRepository(): StripeRepository {
        return StripeRepository()
    }
    @Provides

    fun providePrescriptionRepository(): PrescriptionRepository {
        return PrescriptionRepositoryImpl()
    }

    @Provides

    fun provideDrugRepository(): DrugRepository {
        return DrugRepositoryImpl()
    }


}