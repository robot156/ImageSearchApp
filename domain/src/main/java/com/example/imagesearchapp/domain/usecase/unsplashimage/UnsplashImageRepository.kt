package com.example.imagesearchapp.domain.usecase.unsplashimage

import androidx.paging.PagingData
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import com.example.imagesearchapp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface UnsplashImageRepository {

    suspend fun getSearchUnsplashImages(query : String, page: Int, limit: Int) : List<UnsplashImageEntity>

    fun getKeepUnsplashImages(): Flow<ApiResult<PagingData<UnsplashImageEntity>>>

    suspend fun setKeepUnsplashImage(unsplashImageItem: UnsplashImageEntity)

    suspend fun getKeepUnsplashImage(imageId: String) : UnsplashImageEntity?

    suspend fun deleteKeepUnsplashImage(imageId: String)
}