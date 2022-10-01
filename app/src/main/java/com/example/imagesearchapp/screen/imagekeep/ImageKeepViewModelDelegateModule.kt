package com.example.imagesearchapp.screen.imagekeep

import com.example.imagesearchapp.di.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ImageKeepViewModelDelegateModule {

    @Singleton
    @Provides
    fun provideImageKeepViewModelDelegate(@ApplicationScope applicationScope: CoroutineScope): ImageKeepViewModelDelegate {
        return ImageKeepViewModelDelegateImpl(applicationScope)
    }
}