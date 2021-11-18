package com.example.employeedirection.feature.employee_list.data.repository


import androidx.test.filters.SmallTest
import com.example.employeedirection.common.model.network.RepositoryResponse
import com.example.employeedirection.common.model.network.RetroResponseError
import com.example.employeedirection.common.utils.FileUtil
import com.example.employeedirection.feature.employee_list.data.model.GetEmployeesRequest.*
import com.example.employeedirection.feature.employee_list.data.remote.EmployeesApi
import com.example.employeedirection.feature.employee_list.domain.model.Employees
import com.example.employeedirection.feature.employee_list.domain.repository.EmployeeRepository
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.squareup.okhttp.mockwebserver.MockResponse
import com.squareup.okhttp.mockwebserver.MockWebServer
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@SmallTest
class EmployeeRepositoryImplTest {

    private lateinit var employeeRepository: EmployeeRepository
    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private lateinit var api: EmployeesApi

    @Before
    fun setUp() {
        mockWebServer.play(1000)
        api = Retrofit.Builder()
            .baseUrl("http://localhost:" + mockWebServer.port)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmployeesApi::class.java)
        employeeRepository = EmployeeRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `fetch valid non-empty employee list given 200 response`() {
        runBlocking {
            val bodyString =
                FileUtil.readFileAsTextUsingInputStream("src/test/resources/valid_response.json")
            val expectedResponse = Gson().fromJson(bodyString, Employees::class.java)
            mockWebServer.enqueue(MockResponse().apply {
                setResponseCode(200)
                setBody(bodyString)
            })
            val actualResponse = employeeRepository.getEmployees(GetEmptyEmployees)
            assertThat(actualResponse.isSuccess()).isTrue()
            assertThat((actualResponse as RepositoryResponse.Success).data).isEqualTo(
                expectedResponse
            )
        }
    }

    @Test
    fun `fetch mal employee list given 200 response`() {
        runBlocking {
            val bodyString =
                FileUtil.readFileAsTextUsingInputStream("src/test/resources/mal_response.json")
            val expectedResponse = Gson().fromJson(bodyString, Employees::class.java)
            mockWebServer.enqueue(MockResponse().apply {
                setResponseCode(200)
                setBody(bodyString)
            })
            val actualResponse = employeeRepository.getEmployees(GetMalEmployees)
            assertThat(actualResponse.isSuccess()).isFalse()
            assertThat((actualResponse as RepositoryResponse.Error).error).isEqualTo(
                RetroResponseError.UnHandledError
            )
        }
    }

    @Test
    fun `fetch employee list given error code`() {
        runBlocking {
            mockWebServer.enqueue(MockResponse().apply {
                setResponseCode(500)
                setBody("Internal Error")
            })
            val actualResponse = employeeRepository.getEmployees(GetMalEmployees)
            assertThat(actualResponse.isSuccess()).isFalse()
            assertThat((actualResponse as RepositoryResponse.Error).error).isEqualTo(
                RetroResponseError.UnHandledError
            )
        }
    }

    //TODO can add more exception test, due to time limit, here I didn't add
    // to give some example
    // UnsupportedOperationException from Gson convert
    // IOException
    // RuntimeException
}