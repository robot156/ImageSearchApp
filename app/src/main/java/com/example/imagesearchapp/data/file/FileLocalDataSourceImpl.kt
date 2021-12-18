package com.example.imagesearchapp.data.file

import javax.inject.Inject

class FileLocalDataSourceImpl @Inject constructor(
    private val fileUtil: FileUtil
) : FileLocalDataSource {

    override suspend fun saveImageFile(filePath: String?, fileName: String, byteArray: ByteArray): String? {
        return fileUtil.saveImageToAlbum(fileName, byteArray)
    }
}