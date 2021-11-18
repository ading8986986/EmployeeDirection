package com.example.employeedirection.common.model.network

import androidx.annotation.StringRes
import com.example.employeedirection.R

enum class RetroResponseError(@StringRes val  description: Int) {
    NetWorkError(R.string.internet_error),
    UnHandledError(R.string.technical_error)
}