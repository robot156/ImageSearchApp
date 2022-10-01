package com.example.imagesearchapp.di

import android.content.Context
import com.example.imagesearchapp.data.file.FileLocalDataSource
import com.example.imagesearchapp.data.file.FileLocalDataSourceImpl
import com.example.imagesearchapp.data.file.FileUtil
import com.example.imagesearchapp.data.local.FileSaveRepositoryImpl
import com.example.imagesearchapp.data.local.UnsplashLocalRepositoryImpl
import com.example.imagesearchapp.data.remote.UnsplashRemoteRepositoryImpl
import com.example.imagesearchapp.domain.FileSaveRepository
import com.example.imagesearchapp.domain.UnsplashLocalRepository
import com.example.imagesearchapp.domain.UnsplashRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    fun providerUnsplashRemoteRepository(unsplashRemoteRepositoryImpl: UnsplashRemoteRepositoryImpl): UnsplashRemoteRepository {
        return unsplashRemoteRepositoryImpl
    }

    @Singleton
    @Provides
    fun providerUnsplashLocalRepository(unsplashLocalRepositoryImpl: UnsplashLocalRepositoryImpl): UnsplashLocalRepository {
        return unsplashLocalRepositoryImpl
    }

    @Singleton
    @Provides
    fun providerDownloadRepository(localDataSource: FileLocalDataSource): FileSaveRepository {
        return FileSaveRepositoryImpl(localDataSource)
    }

    @Singleton
    @Provides
    fun providerDownloadLocalDataSource(fileUtil: FileUtil): FileLocalDataSource {
        return FileLocalDataSourceImpl(fileUtil)
    }

    @Singleton
    @Provides
    fun provideFileUtil(@ApplicationContext context: Context): FileUtil {
        return FileUtil(context)
    }
}