package com.example.imagesearchapp.presentation.model

import android.os.Parcelable
import com.example.imagesearchapp.domain.usecase.unsplashimage.entity.UnsplashImageEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UnsplashImageItem(
    val id: String,
    val userName: String,
    val imageUrl: String,
    val imageDescription: String?,
    val keyword: String = ""
) : Parcelable

fun UnsplashImageEntity.mapToItem(): UnsplashImageItem {
    return UnsplashImageItem(
        id = id,
        userName = userName,
        imageUrl = imageUrl,
        imageDescription = imageDescription,
        keyword = keyword
    )
}

fun UnsplashImageItem.mapToEntity(): UnsplashImageEntity {
    return UnsplashImageEntity(
        id = id,
        userName = userName,
        imageUrl = imageUrl,
        imageDescription = imageDescription,
        keyword = keyword
    )
}