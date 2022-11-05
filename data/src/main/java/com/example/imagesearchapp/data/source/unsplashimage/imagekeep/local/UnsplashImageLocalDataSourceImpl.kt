package com.example.imagesearchapp.data.source.unsplashimage.imagekeep.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.imagesearchapp.data.source.unsplashimage.UnsplashImageLocalDataSource
import com.example.imagesearchapp.data.source.unsplashimage.model.UnsplashImageMapper
import com.example.imagesearchapp.data.utils.database.ImageSearchDataBase
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UnsplashImageLocalDataSourceImpl @Inject constructor(
    private val imageSearchDataBase: ImageSearchDataBase
) : UnsplashImageLocalDataSource {

    override fun getKeepUnsplashImages(): Flow<PagingData<UnsplashImageEntity>> = Pager(
        config = PagingConfig(pageSize = KEEP_LOAD_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { imageSearchDataBase.keepUnsplashImage().getKeepUnsplashImages() }
    ).flow.map { pagingData ->
        pagingData.map { UnsplashImageMapper.fromModelToEntity(it) }
    }

    override suspend fun setKeepUnsplashImage(unsplashImage: UnsplashImageEntity) {
        imageSearchDataBase.keepUnsplashImage().insertUnsplashImage(UnsplashImageMapper.fromEntityToModel(unsplashImage))
    }

    override suspend fun getKeepUnsplashImage(imageId: String): UnsplashImageEntity? {
        return imageSearchDataBase.keepUnsplashImage().getUnsplashImage(imageId)?.let {
            UnsplashImageMapper.fromModelToEntity(it)
        }
    }

    override suspend fun deleteKeepUnsplashImage(imageId: String) {
        imageSearchDataBase.keepUnsplashImage().deleteUnsplashImage(imageId)
    }

    companion object {
        private const val KEEP_LOAD_SIZE = 20
    }
}