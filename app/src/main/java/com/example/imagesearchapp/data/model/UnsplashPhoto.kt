package com.example.imagesearchapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashPhoto(
    @SerialName("id")
    val id: String,
    @SerialName("description")
    val description: String?,
    @SerialName("urls")
    val urls: UnsplashPhotoUrls,
    @SerialName("user")
    val user: UnsplashUser
):java.io.Serializable {

    @Serializable
    data class UnsplashPhotoUrls(
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
    ):java.io.Serializable

    @Serializable
    data class UnsplashUser(
        @SerialName("name")
        val name: String,
        @SerialName("username")
        val username: String
    ):java.io.Serializable {
        @SerialName("attributionUrl")
        val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearchApp&utm_medium=referral"
    }
}