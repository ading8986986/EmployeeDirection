package com.example.employeedirection.common.module

import android.app.Application
import com.example.employeedirection.common.model.Constants
import com.example.employeedirection.feature.employee_list.data.remote.EmployeesApi
import com.example.employeedirection.feature.employee_list.data.repository.EmployeeRepositoryImpl
import com.example.employeedirection.feature.employee_list.domain.repository.EmployeeRepository
import com.example.employeedirection.feature.employee_list.domain.user_cases.GetEmployeesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDIModule {

    @Provides
    @Singleton
    fun providesEmployeesApi(app: Application): EmployeesApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(employeesApi: EmployeesApi): EmployeeRepository {
        return EmployeeRepositoryImpl(employeesApi)
    }

    @Provides
    @Singleton
    fun provideGetTaskUseCase(employeeRepository: EmployeeRepository): GetEmployeesUseCase {
        return GetEmployeesUseCase(employeeRepository)
    }

}