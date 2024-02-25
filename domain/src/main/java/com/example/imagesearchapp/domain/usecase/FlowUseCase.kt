package com.example.imagesearchapp.domain.usecase

import com.example.imagesearchapp.domain.utils.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class FlowUseCase<in Params, Type>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(params: Params): Flow<ApiResult<Type>> {
        return execute(params)
            .catch { e -> emit(ApiResult.Error(e as? Exception ?: Exception(e))) }
    }

    abstract fun execute(params: Params) : Flow<ApiResult<Type>>
}