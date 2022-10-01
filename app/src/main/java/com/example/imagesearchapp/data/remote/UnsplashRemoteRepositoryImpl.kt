package com.example.imagesearchapp.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.imagesearchapp.data.model.UnsplashPhoto
import com.example.imagesearchapp.domain.UnsplashRemoteRepository
import com.example.imagesearchapp.service.ServiceApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UnsplashRemoteRepositoryImpl @Inject constructor(
    private val unsplashApi: ServiceApi
) : UnsplashRemoteRepository {

    override fun getSearchResults(query: String): Flow<PagingData<UnsplashPhoto>> =
        Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UnsplashPagingSource(unsplashApi, query)
            }
        ).flow
}