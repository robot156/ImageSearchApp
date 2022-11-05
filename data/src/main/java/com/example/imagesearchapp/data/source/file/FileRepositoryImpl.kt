package com.example.imagesearchapp.data.source.file

import com.example.imagesearchapp.domain.usecase.file.FileRepository
import com.example.imagesearchapp.domain.utils.ResultState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileLocalDataSource: FileLocalDataSource
) : FileRepository {

    override fun saveImageFile(byteArray: ByteArray, filePath: String?, fileName: String): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        emit(ResultState.Success(fileLocalDataSource.saveImageFile(filePath, fileName, byteArray)))
    }
}