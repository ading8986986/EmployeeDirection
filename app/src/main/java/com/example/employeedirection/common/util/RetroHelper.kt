package com.example.employeedirection.common.util

import com.example.employeedirection.common.model.network.RetroResponseError
import com.example.employeedirection.common.model.network.RetroResponseError.*
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

object RetroHelper {

    fun parseThrowable(t: Throwable): RetroResponseError {
        return when (t) {
            is HttpException, is IOException -> NetWorkError
            is TimeoutException -> UnHandledError
            else -> UnHandledError
        }
    }
}