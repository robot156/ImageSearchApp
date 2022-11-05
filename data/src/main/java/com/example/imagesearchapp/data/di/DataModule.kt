package com.example.imagesearchapp.data.di

import android.content.Context
import com.example.imagesearchapp.data.source.file.FileLocalDataSource
import com.example.imagesearchapp.data.source.file.FileRepositoryImpl
import com.example.imagesearchapp.data.source.file.local.FileLocalDataSourceImpl
import com.example.imagesearchapp.data.source.unsplashimage.UnsplashImageLocalDataSource
import com.example.imagesearchapp.data.source.unsplashimage.UnsplashImageRemoteDataSource
import com.example.imagesearchapp.data.source.unsplashimage.UnsplashImageRepositoryImpl
import com.example.imagesearchapp.data.source.unsplashimage.imagekeep.local.UnsplashImageLocalDataSourceImpl
import com.example.imagesearchapp.data.source.unsplashimage.imagesearch.remote.UnsplashImageRemoteDataSourceImpl
import com.example.imagesearchapp.data.utils.database.ImageSearchDataBase
import com.example.imagesearchapp.data.utils.file.FileUtil
import com.example.imagesearchapp.data.utils.service.ServiceApi
import com.example.imagesearchapp.domain.usecase.file.FileRepository
import com.example.imagesearchapp.domain.usecase.unsplashimage.UnsplashImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {

    @Singleton
    @Provides
    internal fun providerUnsplashImageRemoteDataSource(serviceApi: ServiceApi): UnsplashImageRemoteDataSource {
        return UnsplashImageRemoteDataSourceImpl(serviceApi)
    }

    @Singleton
    @Provides
    internal fun providerUnsplashImageLocalDataSource(imageSearchDataBase: ImageSearchDataBase): UnsplashImageLocalDataSource {
        return UnsplashImageLocalDataSourceImpl(imageSearchDataBase)
    }

    @Singleton
    @Provides
    internal fun providerUnsplashImageRepository(unsplashImageLocalDataSource: UnsplashImageLocalDataSource, unsplashImageRemoteDataSource: UnsplashImageRemoteDataSource): UnsplashImageRepository {
        return UnsplashImageRepositoryImpl(unsplashImageLocalDataSource, unsplashImageRemoteDataSource)
    }

    @Singleton
    @Provides
    internal fun providerFileLocalDataSource(fileUtil: FileUtil): FileLocalDataSource {
        return FileLocalDataSourceImpl(fileUtil)
    }

    @Singleton
    @Provides
    internal fun providerFileRepository(fileLocalDataSource: FileLocalDataSource): FileRepository {
        return FileRepositoryImpl(fileLocalDataSource)
    }

    @Singleton
    @Provides
    internal fun provideFileUtil(@ApplicationContext context: Context): FileUtil {
        return FileUtil(context)
    }
}