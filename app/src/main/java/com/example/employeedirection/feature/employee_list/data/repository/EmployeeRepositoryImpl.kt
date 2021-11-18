package com.example.employeedirection.feature.employee_list.data.repository

import com.example.employeedirection.feature.employee_list.data.remote.EmployeesApi
import com.example.employeedirection.feature.employee_list.domain.model.Employees
import com.example.employeedirection.feature.employee_list.domain.repository.EmployeeRepository
import com.example.employeedirection.common.model.network.RepositoryResponse.*
import com.example.employeedirection.common.model.network.RepositoryResponse
import com.example.employeedirection.common.model.network.RetroResponseError.*
import com.example.employeedirection.common.model.network.RetroResponseError
import com.example.employeedirection.common.util.RetroHelper
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest.*
import kotlinx.coroutines.delay
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(private val employeesApi: EmployeesApi) :
    EmployeeRepository {
    override suspend fun getEmployees(getEmployeesRequest: GetEmployeesRequest): RepositoryResponse<Employees> {
        try {
            // delay on purpose to display loading, otherwise it'd too fast
            // in the real work, wouldn't add this line
            delay(1000)
            val result = when (getEmployeesRequest) {
                GetMalEmployees -> employeesApi.getMalEmployees()
                GetEmptyEmployees -> employeesApi.getEmptyEmployees()
                else -> employeesApi.getEmployees()
            }

            if (result.isSuccessful) {
                result.body()?.let {
                    return if (validateResponse(it)) Success(it)
                    else Error(UnHandledError)
                } ?: run {
                    return Error(UnHandledError)
                }
            } else {
                return Error(UnHandledError)
            }
        } catch (t: Throwable) {
            return Error(RetroHelper.parseThrowable(t))
        }
    }

    private fun validateResponse(employees: Employees): Boolean {
        return employees.employees.all {
            !it.isInValidData()
        }
    }
}