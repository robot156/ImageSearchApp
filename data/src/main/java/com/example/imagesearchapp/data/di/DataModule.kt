package com.example.imagesearchapp.data.di

import com.example.imagesearchapp.data.source.file.FileLocalDataSource
import com.example.imagesearchapp.data.source.file.FileRepositoryImpl
import com.example.imagesearchapp.data.source.file.local.FileLocalDataSourceImpl
import com.example.imagesearchapp.data.source.unsplashimage.UnsplashImageLocalDataSource
import com.example.imagesearchapp.data.source.unsplashimage.UnsplashImageRemoteDataSource
import com.example.imagesearchapp.data.source.unsplashimage.UnsplashImageRepositoryImpl
import com.example.imagesearchapp.data.source.unsplashimage.imagekeep.local.UnsplashImageLocalDataSourceImpl
import com.example.imagesearchapp.data.source.unsplashimage.imagesearch.remote.UnsplashImageRemoteDataSourceImpl
import com.example.imagesearchapp.domain.usecase.file.FileRepository
import com.example.imagesearchapp.domain.usecase.unsplashimage.UnsplashImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {

    @Singleton
    @Binds
    internal abstract fun bindUnsplashImageRemoteDataSource(unsplashImageRemoteDataSourceImpl: UnsplashImageRemoteDataSourceImpl): UnsplashImageRemoteDataSource

    @Singleton
    @Binds
    internal abstract fun bindUnsplashImageLocalDataSource(unsplashImageLocalDataSourceImpl: UnsplashImageLocalDataSourceImpl): UnsplashImageLocalDataSource

    @Singleton
    @Binds
    internal abstract fun bindUnsplashImageRepository(unsplashImageRepositoryImpl: UnsplashImageRepositoryImpl): UnsplashImageRepository

    @Singleton
    @Binds
    internal abstract fun bindFileLocalDataSource(fileLocalDataSourceImpl: FileLocalDataSourceImpl): FileLocalDataSource

    @Singleton
    @Binds
    internal abstract fun bindFileRepository(fileRepositoryImpl: FileRepositoryImpl) : FileRepository
}