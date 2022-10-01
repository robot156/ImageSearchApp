package com.example.imagesearchapp.domain

interface FileSaveRepository {

    suspend fun saveImageFileByByteArray(byteArray: ByteArray, filePath: String?, fileName: String): String?
}