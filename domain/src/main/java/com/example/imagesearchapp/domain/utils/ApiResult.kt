package com.example.imagesearchapp.domain.utils

sealed class ApiResult<out R> {
    data object Loading : ApiResult<Nothing>()
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

val ApiResult<*>.succeeded
    get() = this is ApiResult.Success && data != null

val <T> ApiResult<T>.data: T?
    get() = (this as? ApiResult.Success)?.data

fun <T> ApiResult<T>.successOr(fallback: T): T {
    return (this as? ApiResult.Success<T>)?.data ?: fallback
}