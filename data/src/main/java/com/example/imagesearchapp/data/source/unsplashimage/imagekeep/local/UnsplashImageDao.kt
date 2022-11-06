package com.example.imagesearchapp.data.source.unsplashimage.imagekeep.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imagesearchapp.data.source.unsplashimage.model.UnsplashImage

@Dao
internal interface UnsplashImageDao {

    @Query("SELECT * FROM UnsplashImage ORDER BY keyword")
    fun getKeepUnsplashImages(): PagingSource<Int, UnsplashImage>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnsplashImage(unsplashImage: UnsplashImage)

    @Query("SELECT * FROM UnsplashImage WHERE id is NULL OR id = :imageId")
    suspend fun getUnsplashImage(imageId: String): UnsplashImage?

    @Query("DELETE FROM UnsplashImage WHERE id is NULL OR id = :imageId")
    suspend fun deleteUnsplashImage(imageId: String)
}