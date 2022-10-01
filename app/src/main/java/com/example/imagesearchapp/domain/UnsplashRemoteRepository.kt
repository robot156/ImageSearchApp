package com.example.imagesearchapp.domain

import androidx.paging.PagingData
import com.example.imagesearchapp.data.model.UnsplashPhoto
import kotlinx.coroutines.flow.Flow

interface UnsplashRemoteRepository {

    fun getSearchResults(query : String) : Flow<PagingData<UnsplashPhoto>>
}