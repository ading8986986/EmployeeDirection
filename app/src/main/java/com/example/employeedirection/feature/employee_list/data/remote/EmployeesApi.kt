package com.example.employeedirection.feature.employee_list.data.remote

import com.example.employeedirection.feature.employee_list.domain.model.Employees
import retrofit2.Response
import retrofit2.http.GET

interface EmployeesApi {

    @GET(GET_VALID_EMPLOYEE_URL)
    suspend fun getEmployees() : Response<Employees>

    @GET(GET_MAL_EMPLOYEE_URL)
    suspend fun getMalEmployees() : Response<Employees>

    @GET(GET_EMPTY_EMPLOYEE_URL)
    suspend fun getEmptyEmployees() : Response<Employees>

    companion object{
        private const val GET_VALID_EMPLOYEE_URL = "employees.json"
        private const val GET_MAL_EMPLOYEE_URL = "employees_malformed.json"
        private const val GET_EMPTY_EMPLOYEE_URL = "employees_empty.json"
    }
}