package com.example.imagesearchapp.domain.usecase

import com.example.imagesearchapp.domain.utils.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class UseCase<in Params, out Type>(private val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param params the input parameters to run the use case with
     */
    suspend operator fun invoke(params: Params): ResultState<Type> {
        return try {
            // Moving all use case's executions to the injected dispatcher
            // In production code, this is usually the Default dispatcher (background thread)
            // In tests, this becomes a TestCoroutineDispatcher
            withContext(coroutineDispatcher) {
                execute(params).let {
                    ResultState.Success(it)
                }
            }
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(params: Params): Type
}
