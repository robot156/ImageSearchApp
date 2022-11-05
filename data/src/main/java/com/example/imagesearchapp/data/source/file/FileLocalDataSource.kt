package com.example.imagesearchapp.data.source.file

interface FileLocalDataSource {

    suspend fun saveImageFile(filePath: String?, fileName: String, byteArray: ByteArray) : String
}