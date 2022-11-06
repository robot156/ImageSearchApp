package com.example.imagesearchapp.data.source.unsplashimage.imagesearch.remote

import com.example.imagesearchapp.data.source.unsplashimage.UnsplashImageRemoteDataSource
import com.example.imagesearchapp.data.utils.service.ServiceApi
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import javax.inject.Inject

internal class UnsplashImageRemoteDataSourceImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : UnsplashImageRemoteDataSource {

    override suspend fun getSearchUnsplashImages(query: String, page: Int, limit: Int): List<UnsplashImageEntity> {
        return serviceApi.getSearchUnsplashImages(query,page, limit).results.map {
            UnsplashImageEntity(
                id = it.id,
                userName = it.user.name,
                imageUrl = it.urls.regular,
                imageDescription = it.description
            )
        }
    }
}