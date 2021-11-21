package com.example.imagesearchapp.domain

import androidx.paging.PagingData
import com.example.imagesearchapp.data.model.UnsplashPhoto
import kotlinx.coroutines.flow.Flow

interface UnSplashRepository {
    fun getSearchResults(query : String) : Flow<PagingData<UnsplashPhoto>>
}