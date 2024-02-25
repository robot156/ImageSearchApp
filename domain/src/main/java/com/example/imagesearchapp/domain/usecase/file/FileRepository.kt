package com.example.imagesearchapp.domain.usecase.file

import com.example.imagesearchapp.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface FileRepository {

    fun saveImageFile(byteArray: ByteArray, filePath: String?, fileName: String): Flow<ApiResult<String>>
}