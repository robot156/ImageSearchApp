package com.example.imagesearchapp.data.source.file.local

import com.example.imagesearchapp.data.source.file.FileLocalDataSource
import com.example.imagesearchapp.data.utils.file.FileUtil
import javax.inject.Inject

internal class FileLocalDataSourceImpl @Inject constructor(
    private val fileUtil: FileUtil
) : FileLocalDataSource {

    override suspend fun saveImageFile(filePath: String?, fileName: String, byteArray: ByteArray): String {
        return fileUtil.saveImageToAlbum(fileName, byteArray)
    }
}