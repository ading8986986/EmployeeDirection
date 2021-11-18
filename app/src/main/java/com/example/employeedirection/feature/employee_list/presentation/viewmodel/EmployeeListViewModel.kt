package com.example.employeedirection.feature.employee_list.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.employeedirection.R
import com.example.employeedirection.feature.employee_list.domain.model.Employees
import com.example.employeedirection.feature.employee_list.domain.user_cases.GetEmployeesUseCase
import com.example.employeedirection.common.model.Event
import com.example.employeedirection.common.model.UseCaseResponse.*
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest
import com.example.employeedirection.feature.employee_list.domain.model.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeListViewModel @Inject constructor(
    private val getEmployeesUseCase: GetEmployeesUseCase
) : ViewModel() {
    private val _loadingViewVisibility = MutableLiveData(false)
    val loadingViewVisibility: LiveData<Boolean> = _loadingViewVisibility

    // use it to provide hint
    private val _errorEvent: MutableLiveData<Event<Int>> = MutableLiveData()
    val errorEvent: LiveData<Event<Int>> = _errorEvent

    private val _employeeList: MutableLiveData<List<Employee>> = MutableLiveData()
    val employeeList: LiveData<List<Employee>> = _employeeList

    private var currentGetEmployeesRequest: GetEmployeesRequest? = null

    private fun fetchEmployees(
        getEmployeesRequest: GetEmployeesRequest =
            GetEmployeesRequest.GetValidEmployees
    ) {
        currentGetEmployeesRequest = getEmployeesRequest
        _employeeList.value = emptyList()

        viewModelScope.launch {
            getEmployeesUseCase.getEmployees(getEmployeesRequest).collect {
                when (it) {
                    is Loading -> {
                        _loadingViewVisibility.value = true
                    }
                    is Success<Employees> -> {
                        if (it.data.employees.isEmpty()) {
                            _errorEvent.value = Event(R.string.empty_employees_list)
                        } else {
                            _employeeList.value = it.data.employees
                        }
                        _loadingViewVisibility.value = false
                    }
                    is Error -> {
                        _errorEvent.value = Event(it.message)
                        _loadingViewVisibility.value = false
                    }
                }
            }
        }
    }

    fun loadValidEmployList() {
        if (currentGetEmployeesRequest != GetEmployeesRequest.GetValidEmployees)
            fetchEmployees()
    }

    fun loadEmptyEmployList() {
        if (currentGetEmployeesRequest != GetEmployeesRequest.GetEmptyEmployees)
            fetchEmployees(GetEmployeesRequest.GetEmptyEmployees)
    }

    fun loadMalEmployList() {
        if (currentGetEmployeesRequest != GetEmployeesRequest.GetMalEmployees)
            fetchEmployees(GetEmployeesRequest.GetMalEmployees)
    }
}