package com.example.imagesearchapp.data.utils.service

import com.example.imagesearchapp.data.BuildConfig
import com.example.imagesearchapp.data.source.unsplashimage.model.PagedOfUnsplashImageResults
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

internal interface ServiceApi {

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${BuildConfig.UNSPALSH_API_KEY}")
    @GET("search/photos")
    suspend fun getSearchUnsplashImages(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): PagedOfUnsplashImageResults
}