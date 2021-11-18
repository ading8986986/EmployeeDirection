package com.example.employeedirection.feature.employee_list.domain.user_cases.helper

import com.example.employeedirection.common.model.network.RepositoryResponse
import com.example.employeedirection.feature.employee_list.common.util.ResponseUtil
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest.*
import com.example.employeedirection.feature.employee_list.domain.model.Employees
import com.example.employeedirection.feature.employee_list.domain.repository.EmployeeRepository

object MockEmployeeRepositoryImpl : EmployeeRepository {
    override suspend fun getEmployees(getEmployeesRequest: GetEmployeesRequest): RepositoryResponse<Employees> {
        return when (getEmployeesRequest) {
            GetValidEmployees -> {
                print("GetValidEmployees")
                ResponseUtil.generateValidRetrofitResponse()
            }
            GetMalEmployees -> ResponseUtil.generateErrorRetrofitResponse()
            GetEmptyEmployees -> ResponseUtil.generateEmptyRetrofitResponse()
        }
    }
}