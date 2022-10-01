package com.example.imagesearchapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imagesearchapp.data.local.UnsplashPhotoDao
import com.example.imagesearchapp.data.model.UnsplashPhotoItem

@Database(
    entities = [UnsplashPhotoItem::class],
    version = 1,
    exportSchema = false
)
abstract class ImageSearchDataBase : RoomDatabase() {

    abstract fun keepUnsplashPhoto(): UnsplashPhotoDao

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