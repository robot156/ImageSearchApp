package com.example.imagesearchapp.domain.usecase.file

import com.example.imagesearchapp.domain.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    fun saveImageFile(byteArray: ByteArray, filePath: String?, fileName: String): Flow<ResultState<String>>
}