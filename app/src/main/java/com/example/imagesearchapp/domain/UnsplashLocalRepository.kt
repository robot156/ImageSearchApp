package com.example.imagesearchapp.domain

import androidx.paging.PagingData
import com.example.imagesearchapp.data.model.UnsplashPhotoItem
import kotlinx.coroutines.flow.Flow

interface UnsplashLocalRepository {

    fun getUnSplashPhotos(): Flow<PagingData<UnsplashPhotoItem>>

    suspend fun setUnSplash(unsplashPhotoItem: UnsplashPhotoItem)

    suspend fun getUnSplash(imageId: String) : UnsplashPhotoItem?

    suspend fun deleteUnSplashPhoto(imageId: String)
}