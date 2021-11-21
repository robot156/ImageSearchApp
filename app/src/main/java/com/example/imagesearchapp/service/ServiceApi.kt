package com.example.imagesearchapp.service

import com.example.imagesearchapp.BuildConfig
import com.example.imagesearchapp.data.model.UnsplashResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ServiceApi {

    @Headers("Accept-Version: v1", "Authorization: Client-ID ${BuildConfig.UNSPALSH_API_KEY}")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UnsplashResponse
}