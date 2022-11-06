package com.example.imagesearchapp.data.source.unsplashimage.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imagesearchapp.data.source.Model

@Entity(tableName = "UnsplashImage")
internal data class UnsplashImage(
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
) : Model