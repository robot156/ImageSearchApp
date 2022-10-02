package com.example.imagesearchapp.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.example.imagesearchapp.data.model.UnsplashPhotoItem

@Dao
interface UnsplashPhotoDao {

    @Query("SELECT * FROM UnsplashPhoto ORDER BY keyword")
    fun getKeepImages(): PagingSource<Int, UnsplashPhotoItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImagePhoto(unsplashPhotoItem: UnsplashPhotoItem)

    @Query("SELECT * FROM UnsplashPhoto WHERE id is NULL OR id = :imageId")
    suspend fun getImagePhoto(imageId: String): UnsplashPhotoItem?

    @Query("DELETE FROM UnsplashPhoto WHERE id is NULL OR id = :imageId")
    suspend fun deleteImagePhoto(imageId: String)
}