package com.example.imagesearchapp.di

import android.content.Context
import com.example.imagesearchapp.data.file.FileLocalDataSource
import com.example.imagesearchapp.data.file.FileLocalDataSourceImpl
import com.example.imagesearchapp.data.file.FileUtil
import com.example.imagesearchapp.data.remote.FileSaveRepositoryImpl
import com.example.imagesearchapp.data.remote.UnSplashRepositoryImpl
import com.example.imagesearchapp.domain.FileSaveRepository
import com.example.imagesearchapp.domain.UnSplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    fun providerUnsplashSearch(unSplashRepositoryImpl: UnSplashRepositoryImpl): UnSplashRepository {
        return unSplashRepositoryImpl
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