package com.example.imagesearchapp.data.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imagesearchapp.data.source.unsplashimage.imagekeep.local.UnsplashImageDao
import com.example.imagesearchapp.data.source.unsplashimage.model.UnsplashImage

@Database(
    entities = [UnsplashImage::class],
    version = 1,
    exportSchema = false
)
internal abstract class ImageSearchDataBase : RoomDatabase() {

    abstract fun keepUnsplashImage(): UnsplashImageDao

    companion object {
        private const val databaseName = "image-search-db"

        fun buildDatabase(context: Context): ImageSearchDataBase {
            return Room
                .databaseBuilder(context, ImageSearchDataBase::class.java, databaseName)
                .enableMultiInstanceInvalidation()
                .build()
        }
    }
}