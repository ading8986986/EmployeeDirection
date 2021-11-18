package com.example.employeedirection.feature.employee_list.domain.user_cases

import androidx.test.filters.SmallTest
import com.example.employeedirection.feature.employee_list.common.util.ResponseUtil
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest.*
import com.example.employeedirection.feature.employee_list.domain.user_cases.helper.MockEmployeeRepositoryImpl
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Test

@SmallTest
class GetEmployeesUseCaseTest {

    private val getEmployeesUseCase = GetEmployeesUseCase(MockEmployeeRepositoryImpl)

    @Test
    fun `get valid non-empty employees, expect collect Loading and Success from flow`() {
        runBlocking {
            val outFlow = getEmployeesUseCase.getEmployees(GetValidEmployees)
            assertThat(outFlow.first()).isEqualTo(ResponseUtil.generateLoadingUseCaseResponse())
            assertThat(
                outFlow.drop(1).first()
            ).isEqualTo(ResponseUtil.generateSuccessUseCaseResponse())
        }
    }

    @Test
    fun `get valid mal employees, expect collect Loading and Error from flow`() {
        runBlocking {
            val outFlow = getEmployeesUseCase.getEmployees(GetMalEmployees)
            assertThat(outFlow.first()).isEqualTo(ResponseUtil.generateLoadingUseCaseResponse())
            assertThat(
                outFlow.drop(1).first()
            ).isEqualTo(ResponseUtil.generateErrorUseCaseResponse())
        }
    }
}