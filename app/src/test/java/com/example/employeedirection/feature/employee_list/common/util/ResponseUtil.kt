package com.example.employeedirection.feature.employee_list.common.util

import com.example.employeedirection.common.model.UseCaseResponse
import com.example.employeedirection.common.model.network.RepositoryResponse
import com.example.employeedirection.common.model.network.RetroResponseError
import com.example.employeedirection.common.utils.FileUtil
import com.example.employeedirection.feature.employee_list.domain.model.Employees
import com.google.gson.Gson

object ResponseUtil {
    fun generateValidRetrofitResponse(): RepositoryResponse.Success<Employees> {
        val validBodyString =
            FileUtil.readFileAsTextUsingInputStream("src/test/resources/valid_response.json")
        return RepositoryResponse.Success(
            Gson().fromJson(validBodyString, Employees::class.java)
        )
    }

    fun generateEmptyRetrofitResponse(): RepositoryResponse.Success<Employees> {
        val bodyString =
            FileUtil.readFileAsTextUsingInputStream("src/test/resources/empty_response.json")
        return RepositoryResponse.Success(
            Gson().fromJson(bodyString, Employees::class.java)
        )
    }

    fun generateErrorRetrofitResponse(): RepositoryResponse.Error {
        return RepositoryResponse.Error(
            RetroResponseError.UnHandledError
        )
    }

    fun generateLoadingUseCaseResponse(): UseCaseResponse.Loading {
        return UseCaseResponse.Loading
    }

    fun generateSuccessUseCaseResponse(): UseCaseResponse.Success<Employees> {
        val bodyString =
            FileUtil.readFileAsTextUsingInputStream("src/test/resources/valid_response.json")
        return UseCaseResponse.Success(
            Gson().fromJson(bodyString, Employees::class.java)
        )
    }

    fun generateEmptySuccessUseCaseResponse(): UseCaseResponse.Success<Employees> {
        val bodyString =
            FileUtil.readFileAsTextUsingInputStream("src/test/resources/empty_response.json")
        return UseCaseResponse.Success(
            Gson().fromJson(bodyString, Employees::class.java)
        )
    }

    fun generateErrorUseCaseResponse(): UseCaseResponse.Error {
        return UseCaseResponse.Error(
            RetroResponseError.UnHandledError.description
        )
    }
}