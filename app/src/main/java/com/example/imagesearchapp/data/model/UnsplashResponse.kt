package com.example.imagesearchapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashResponse(
    @SerialName("total")
    val total: Long,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("results")
    val results: List<UnsplashPhoto>
) : java.io.Serializable