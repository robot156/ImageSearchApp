package com.example.imagesearchapp.data.source.file

import com.example.imagesearchapp.domain.usecase.file.FileRepository
import com.example.imagesearchapp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileLocalDataSource: FileLocalDataSource
) : FileRepository {

    override fun saveImageFile(byteArray: ByteArray, filePath: String?, fileName: String): Flow<ApiResult<String>> = flow {
        emit(ApiResult.Loading)
        emit(ApiResult.Success(fileLocalDataSource.saveImageFile(filePath, fileName, byteArray)))
    }
}