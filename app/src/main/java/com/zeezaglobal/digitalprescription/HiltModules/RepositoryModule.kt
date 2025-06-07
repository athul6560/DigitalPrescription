package com.zeezaglobal.digitalprescription.HiltModules

import com.zeezaglobal.digitalprescription.Repository.StripeRepository
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
}