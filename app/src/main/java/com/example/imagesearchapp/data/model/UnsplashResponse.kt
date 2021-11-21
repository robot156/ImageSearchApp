package com.example.imagesearchapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UnsplashResponse(
    val results: List<UnsplashPhoto>
) : Parcelable