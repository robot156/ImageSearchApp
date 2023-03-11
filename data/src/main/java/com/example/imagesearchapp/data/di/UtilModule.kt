package com.example.imagesearchapp.data.di

import android.content.Context
import com.example.imagesearchapp.data.utils.file.FileUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal class UtilModule {

    @Singleton
    @Provides
    internal fun provideFileUtil(@ApplicationContext context: Context): FileUtil {
        return FileUtil(context)
    }
}