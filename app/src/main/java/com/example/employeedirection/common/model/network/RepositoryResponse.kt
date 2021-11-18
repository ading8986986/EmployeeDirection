package com.example.employeedirection.common.model.network

sealed class RepositoryResponse<out T> {
    data class Success<T>(val data: T) : RepositoryResponse<T>()
    data class Error(val error: RetroResponseError) : RepositoryResponse<Nothing>()

    fun isSuccess(): Boolean {
        return this is Success
    }
}
