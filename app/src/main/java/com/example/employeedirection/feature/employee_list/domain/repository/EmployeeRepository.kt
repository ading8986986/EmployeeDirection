package com.example.employeedirection.feature.employee_list.domain.repository

import com.example.employeedirection.feature.employee_list.domain.model.Employees
import com.example.employeedirection.common.model.network.RepositoryResponse
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest

interface EmployeeRepository {
    suspend fun getEmployees(getEmployeesRequest: GetEmployeesRequest) : RepositoryResponse<Employees>
}