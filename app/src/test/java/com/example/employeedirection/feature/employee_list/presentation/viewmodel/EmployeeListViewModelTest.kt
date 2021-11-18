package com.example.employeedirection.feature.employee_list.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.employeedirection.common.MainCoroutineRule
import com.example.employeedirection.common.model.Event
import com.example.employeedirection.feature.employee_list.common.util.ResponseUtil
import com.example.employeedirection.feature.employee_list.common.util.getOrAwaitValueTest
import com.example.employeedirection.feature.employee_list.domain.model.Employee
import com.example.employeedirection.feature.employee_list.domain.user_cases.GetEmployeesUseCase
import com.example.employeedirection.R
import com.example.employeedirection.feature.employee_list.domain.user_cases.helper.MockEmployeeRepositoryImpl

import com.google.common.truth.Truth.assertThat

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*


class EmployeeListViewModelTest {

    private lateinit var viewModel: EmployeeListViewModel
    private val getEmployeesUseCase = GetEmployeesUseCase(MockEmployeeRepositoryImpl)


    private lateinit var loadingVisibilityObserver: Observer<Boolean>
    private lateinit var errorEventObserver: Observer<Event<Int>>
    private lateinit var employeeListObserver: Observer<List<Employee>>

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        viewModel = EmployeeListViewModel(getEmployeesUseCase)
        loadingVisibilityObserver = mock(Observer::class.java) as Observer<Boolean>
        errorEventObserver = mock(Observer::class.java) as Observer<Event<Int>>
        employeeListObserver = mock(Observer::class.java) as Observer<List<Employee>>
        viewModel.loadingViewVisibility.observeForever(loadingVisibilityObserver)
        viewModel.errorEvent.observeForever(errorEventObserver)
        viewModel.employeeList.observeForever(employeeListObserver)
    }

    @After
    fun tearDown() {
        viewModel.loadingViewVisibility.removeObserver(loadingVisibilityObserver)
        viewModel.errorEvent.removeObserver(errorEventObserver)
        viewModel.employeeList.removeObserver(employeeListObserver)
    }

    @Test
    fun `load non-empty list, show loading and clear current employee list, then hide loading and upate current employee list`() =
        runBlocking {
            val expectedResponse = ResponseUtil.generateSuccessUseCaseResponse()
            viewModel.loadValidEmployList()


            // show loading spinner, and clean the current UI list
            verify(loadingVisibilityObserver).onChanged(true)
            verify(employeeListObserver).onChanged(emptyList())

            assertThat(viewModel.employeeList.getOrAwaitValueTest()).isEqualTo(expectedResponse.data.employees)
            assertThat(viewModel.loadingViewVisibility.getOrAwaitValueTest()).isFalse()
        }

    @Test
    fun `load empty list, show loading and clear current employee list, then hide loading and show hint of empty employee list`() =
        runBlocking {
            viewModel.loadEmptyEmployList()
            // show loading spinner, and clean the current UI list
            verify(loadingVisibilityObserver).onChanged(true)
            verify(employeeListObserver).onChanged(emptyList())

            assertThat(viewModel.loadingViewVisibility.getOrAwaitValueTest()).isFalse()
            assertThat(
                viewModel.errorEvent.getOrAwaitValueTest().getContentIfNotHandled()
            ).isEqualTo(R.string.empty_employees_list)
        }

    @Test
    fun `load mal list, show loading and clear current employee list, then hide loading and show hint of error`() =
        runBlocking {

            viewModel.loadMalEmployList()
            // show loading spinner, and clean the current UI list
            verify(loadingVisibilityObserver).onChanged(true)
            verify(employeeListObserver).onChanged(emptyList())

            assertThat(viewModel.loadingViewVisibility.getOrAwaitValueTest()).isFalse()
            assertThat(
                viewModel.errorEvent.getOrAwaitValueTest().getContentIfNotHandled()
            ).isEqualTo(R.string.technical_error)
        }
}
