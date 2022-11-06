package com.example.imagesearchapp.data.source.unsplashimage

import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity

interface UnsplashImageRemoteDataSource {

    suspend fun getSearchUnsplashImages(query: String, page: Int, limit: Int): List<UnsplashImageEntity>
}