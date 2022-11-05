package com.example.imagesearchapp.data.utils.file

import android.content.ContentValues
import android.content.Context
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject

class FileUtil @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val contentResolver = context.contentResolver
    private val contentValues = ContentValues()

    fun saveImageToAlbum(fileName: String, byteArray: ByteArray): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            with(contentValues) {
                put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "unsplash")
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, "image/*")
                put(MediaStore.Images.Media.IS_PENDING, 1)
                put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                put(MediaStore.Images.Media.BUCKET_ID, fileName)
            }

            val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val item = contentResolver.insert(collection, contentValues)!!
            val pdf: ParcelFileDescriptor? = contentResolver.openFileDescriptor(item, "w", null)
            pdf?.use {
                FileOutputStream(it.fileDescriptor).use { outputStream ->
                    try {
                        outputStream.write(byteArray)
                        contentValues.clear()
                        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                        contentResolver.update(item, contentValues, null, null)
                        item.toString()
                    } finally {
                        contentValues.clear()
                        outputStream.close()
                    }
                }
            } ?: throw Exception("ParcelFileDescriptor is Null!", null)
        } else {
            val futureStudioIconFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + File.separator + "unsplash")
            if (!futureStudioIconFile.exists()) {
                futureStudioIconFile.mkdir()
            }
            val file = File(futureStudioIconFile, fileName)
            try {
                file.writeBytes(byteArray)
                with(contentValues) {
                    put(MediaStore.Images.Media.TITLE, fileName)
                    put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
                    put(MediaStore.Images.Media.BUCKET_ID, fileName)
                    put(MediaStore.Images.Media.DATA, file.absolutePath)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/*")
                }
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null, null)
                file.absoluteFile.toString()
            } finally {
                contentValues.clear()
            }
        }
    }
}