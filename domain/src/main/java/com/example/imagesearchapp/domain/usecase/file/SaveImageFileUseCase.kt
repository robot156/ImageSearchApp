package com.example.imagesearchapp.domain.usecase.file

import com.example.imagesearchapp.domain.di.IoDispatcher
import com.example.imagesearchapp.domain.usecase.FlowUseCase
import com.example.imagesearchapp.domain.utils.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

open class SaveImageFileUseCase @Inject constructor(
    private val fileRepository: FileRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : FlowUseCase<SaveImageFileUseCase.Params, String>(ioDispatcher) {

    override fun execute(params: Params): Flow<ResultState<String>> = fileRepository.saveImageFile(
        byteArray = params.byteArray,
        filePath = params.filePath,
        fileName = params.fileName,
    )

    data class Params(
        val byteArray: ByteArray,
        val filePath: String? = null,
        val fileName: String
    )
}