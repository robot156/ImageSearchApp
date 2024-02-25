package com.example.imagesearchapp.data.source.unsplashimage.model

import com.example.imagesearchapp.data.source.Model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class UnsplashImageResult(
    @SerialName("id")
    val id: String,
    @SerialName("description")
    val description: String?,
    @SerialName("urls")
    val urls: UnsplashImageUrls,
    @SerialName("user")
    val user: UnsplashUser
) : Model {

    @Serializable
    data class UnsplashImageUrls(
        @SerialName("raw")
        val raw: String,
        @SerialName("full")
        val full: String,
        @SerialName("regular")
        val regular: String,
        @SerialName("small")
        val small: String,
        @SerialName("thumb")
        val thumb: String,
    ) : Model

    @Serializable
    data class UnsplashUser(
        @SerialName("name")
        val name: String,
        @SerialName("username")
        val username: String
    ) : Model
}