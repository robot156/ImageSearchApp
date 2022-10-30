package com.example.imagesearchapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "UnsplashPhoto")
@Parcelize
data class UnsplashPhotoItem(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String,
    @ColumnInfo(name = "imageDescription")
    val imageDescription: String?,
    @ColumnInfo(name = "keyword")
    val keyword: String = ""
) : Parcelable