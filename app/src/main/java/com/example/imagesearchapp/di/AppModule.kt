package com.example.imagesearchapp.di

import com.example.imagesearchapp.data.remote.UnSplashRepositoryImpl
import com.example.imagesearchapp.domain.UnSplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun providerUnsplashSearch(impl: UnSplashRepositoryImpl) : UnSplashRepository {
        return impl
    }
}