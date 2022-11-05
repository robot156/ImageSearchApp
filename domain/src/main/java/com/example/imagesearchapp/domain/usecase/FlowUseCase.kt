package com.example.imagesearchapp.domain.usecase

import com.example.imagesearchapp.domain.utils.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

abstract class FlowUseCase<in Params, Type>(private val coroutineDispatcher: CoroutineDispatcher) {

    operator fun invoke(params: Params): Flow<ResultState<Type>> {
        return execute(params)
            .catch { e -> emit(ResultState.Error(e as? Exception ?: Exception(e))) }
    }

    abstract fun execute(params: Params) : Flow<ResultState<Type>>
}