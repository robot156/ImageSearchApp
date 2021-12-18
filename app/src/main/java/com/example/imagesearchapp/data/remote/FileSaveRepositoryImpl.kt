package com.example.imagesearchapp.data.remote

import com.example.imagesearchapp.data.file.FileLocalDataSource
import com.example.imagesearchapp.domain.FileSaveRepository
import javax.inject.Inject

class FileSaveRepositoryImpl @Inject constructor(
    private val localDataSource: FileLocalDataSource
) : FileSaveRepository {

    override suspend fun saveImageFileByByteArray(byteArray: ByteArray, filePath: String?, fileName: String): String? {
        return localDataSource.saveImageFile(filePath, fileName, byteArray)
    }
}