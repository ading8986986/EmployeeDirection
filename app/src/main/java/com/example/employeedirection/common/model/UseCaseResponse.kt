package com.example.employeedirection.common.model

import androidx.annotation.StringRes

sealed class UseCaseResponse<out T>() {
    data class Success<T>(val data: T) : UseCaseResponse<T>()
    object Loading : UseCaseResponse<Nothing>()
    data class Error(@StringRes val message: Int) : UseCaseResponse<Nothing>()
}

