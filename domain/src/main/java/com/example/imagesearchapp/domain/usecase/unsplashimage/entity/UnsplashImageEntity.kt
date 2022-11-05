package com.example.imagesearchapp.domain.usecase.unsplashimage.entity

import com.example.imagesearchapp.domain.usecase.Entity

data class UnsplashImageEntity(
    val id: String,
    val userName: String,
    val imageUrl: String,
    val imageDescription: String?,
    val keyword: String = ""
): Entity