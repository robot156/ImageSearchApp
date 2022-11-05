package com.example.imagesearchapp.data.source.unsplashimage

import androidx.paging.PagingData
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import kotlinx.coroutines.flow.Flow

interface UnsplashImageLocalDataSource {

    fun getKeepUnsplashImages(): Flow<PagingData<UnsplashImageEntity>>

    suspend fun setKeepUnsplashImage(unsplashImage: UnsplashImageEntity)

    suspend fun getKeepUnsplashImage(imageId: String) : UnsplashImageEntity?

    suspend fun deleteKeepUnsplashImage(imageId: String)
}