package com.example.imagesearchapp.domain.utils

sealed class ResultState<out R> {
    object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val exception: Exception) : ResultState<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

val ResultState<*>.succeeded
    get() = this is ResultState.Success && data != null

val <T> ResultState<T>.data: T?
    get() = (this as? ResultState.Success)?.data

fun <T> ResultState<T>.successOr(fallback: T): T {
    return (this as? ResultState.Success<T>)?.data ?: fallback
}