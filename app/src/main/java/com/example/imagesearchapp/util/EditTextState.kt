package com.example.imagesearchapp.util

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class EditTextState {

    @Parcelize
    object Success : EditTextState(), Parcelable

    @Parcelize
    data class Error(@StringRes val stringRes: Int) : EditTextState(), Parcelable

    @Parcelize
    object Loading : EditTextState(), Parcelable

    override fun toString(): String {
        return when (this) {
            is Success -> "Success"
            is Error -> "Error"
            Loading -> "Loading"
        }
    }
}

val EditTextState?.Success
    get() = this is EditTextState.Success