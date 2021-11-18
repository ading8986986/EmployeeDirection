package com.example.employeedirection.feature.employee_list.domain.user_cases

import com.example.employeedirection.feature.employee_list.domain.model.Employees
import com.example.employeedirection.feature.employee_list.domain.repository.EmployeeRepository
import com.example.employeedirection.common.model.network.RepositoryResponse
import com.example.employeedirection.common.model.UseCaseResponse
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEmployeesUseCase @Inject constructor(private val repository: EmployeeRepository) {
    suspend fun getEmployees(
        getEmployeesRequest: GetEmployeesRequest
    ): Flow<UseCaseResponse<Employees>> {
        return flow {
            emit(UseCaseResponse.Loading)
            val response = repository.getEmployees(getEmployeesRequest)
            emit(
                if (response.isSuccess()) {
                    UseCaseResponse.Success((response as RepositoryResponse.Success).data)
                } else {
                    UseCaseResponse.Error((response as RepositoryResponse.Error).error.description)
                }
            )
        }
    }


}