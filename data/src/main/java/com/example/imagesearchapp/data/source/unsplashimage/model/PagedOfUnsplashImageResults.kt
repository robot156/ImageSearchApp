package com.example.imagesearchapp.data.source.unsplashimage.model

import com.example.imagesearchapp.data.source.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PagedOfUnsplashImageResults(
    @SerialName("total")
    val total: Long,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("results")
    val results: List<UnsplashImageResult>
) : Model