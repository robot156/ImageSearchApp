package com.example.imagesearchapp.data.source.unsplashimage

import androidx.paging.PagingData
import com.example.imagesearchapp.domain.usecase.unsplashimage.UnsplashImageRepository
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import com.example.imagesearchapp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UnsplashImageRepositoryImpl @Inject constructor(
    private val unsplashImageLocalDataSource: UnsplashImageLocalDataSource,
    private val unsplashImageRemoteDataSource: UnsplashImageRemoteDataSource
) : UnsplashImageRepository {

    override suspend fun getSearchUnsplashImages(query: String, page: Int, limit: Int): List<UnsplashImageEntity> {
        return unsplashImageRemoteDataSource.getSearchUnsplashImages(query, page, limit)
    }

    override fun getKeepUnsplashImages(): Flow<ApiResult<PagingData<UnsplashImageEntity>>> {
        return unsplashImageLocalDataSource.getKeepUnsplashImages().map { ApiResult.Success(it) }
    }

    override suspend fun setKeepUnsplashImage(unsplashImageItem: UnsplashImageEntity) {
        unsplashImageLocalDataSource.setKeepUnsplashImage(unsplashImageItem)
    }

    override suspend fun getKeepUnsplashImage(imageId: String): UnsplashImageEntity? {
        return unsplashImageLocalDataSource.getKeepUnsplashImage(imageId)
    }

    override suspend fun deleteKeepUnsplashImage(imageId: String) {
        unsplashImageLocalDataSource.deleteKeepUnsplashImage(imageId)
    }
}