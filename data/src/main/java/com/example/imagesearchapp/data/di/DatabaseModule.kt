package com.example.imagesearchapp.data.di

import android.content.Context
import com.example.imagesearchapp.data.utils.database.ImageSearchDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    internal fun providerImageSearchDatabase(
        @ApplicationContext context: Context
    ): ImageSearchDataBase = ImageSearchDataBase.buildDatabase(context)
}