package com.example.ims3000.di

import com.example.ims3000.api.ApiInterface
import com.example.ims3000.api.ApiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideApiRepository(apiInterface: ApiInterface): ApiRepository {
        return ApiRepository(apiInterface)
    }

}