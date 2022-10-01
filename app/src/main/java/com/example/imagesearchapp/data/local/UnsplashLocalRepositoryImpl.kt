package com.example.imagesearchapp.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imagesearchapp.data.database.ImageSearchDataBase
import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import com.example.imagesearchapp.domain.UnsplashLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashLocalRepositoryImpl @Inject constructor(
    private val imageSearchDataBase: ImageSearchDataBase
) : UnsplashLocalRepository {

    override fun getUnSplashPhotos(): Flow<PagingData<UnsplashPhotoItem>> = Pager(
        config = PagingConfig(pageSize = KEEP_LOAD_SIZE, enablePlaceholders = false),
        pagingSourceFactory = { imageSearchDataBase.keepUnsplashPhoto().getKeepImages() }
    ).flow

    override suspend fun setUnSplash(unsplashPhotoItem: UnsplashPhotoItem) {
        imageSearchDataBase.keepUnsplashPhoto().insertImagePhoto(unsplashPhotoItem)
    }

    override suspend fun getUnSplash(imageId: String): UnsplashPhotoItem? {
        return imageSearchDataBase.keepUnsplashPhoto().getImagePhoto(imageId)
    }

    override suspend fun deleteUnSplashPhoto(imageId: String) {
        imageSearchDataBase.keepUnsplashPhoto().deleteImagePhoto(imageId)
    }

    companion object {
        private const val KEEP_LOAD_SIZE = 20
    }
}