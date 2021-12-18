package com.example.imagesearchapp.data.file

interface FileLocalDataSource {

    suspend fun saveImageFile(filePath: String?, fileName: String, byteArray: ByteArray): String?
}