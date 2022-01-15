package io.appicenter.birthdayapp.feature_birthday_list.data.repository

import com.google.common.truth.Truth
import com.google.gson.GsonBuilder
import io.appicenter.birthdayapp.common.Result
import io.appicenter.birthdayapp.feature_birthday_list.data.remote.UsersApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.FileReader
import java.util.concurrent.TimeUnit

class UsersRepositoryImplTest {
    private lateinit var underTest: UsersRepositoryImpl

    private val mockServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1000, TimeUnit.MILLISECONDS)
        .readTimeout(1000, TimeUnit.MILLISECONDS)
        .writeTimeout(1000, TimeUnit.MILLISECONDS)
        .build()

    private val parser = GsonBuilder().setLenient().create()

    private val api = Retrofit.Builder()
        .baseUrl(mockServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(parser))
        .build()
        .create(UsersApi::class.java)

    @Before
    fun setUp() {
        underTest = UsersRepositoryImpl(api)
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }

    @Test
    fun `When server response is 200, then it should return users list`() =
        runBlocking {

            val response = MockResponse().setResponseCode(200)
                .setBody(readTestFiles("users_200.json")!!)
            mockServer.enqueue(response)

            val users = (underTest.getUsers() as Result.Success).data

            Truth.assertThat(users)
                .hasSize(7)

            Truth.assertThat(users)
                .containsNoDuplicates()

        }

    @Test
    fun `When server response is 400, then it should emit Result Error`() =
        runBlocking {

            val response = MockResponse().setResponseCode(400)
            mockServer.enqueue(response)

            val err = (underTest.getUsers() as Result.Error).err

            print(err.message)

            Truth.assertThat(err.message)
                .contains("HTTP 400")

        }


    private fun readTestFiles(filename: String): String? {
        val fr = FileReader("src/test/resources/$filename")
        val sb = StringBuilder()
        var i: Int
        while (fr.read().also { i = it } != -1) {
            sb.append(i.toChar())
        }
        return sb.toString()
    }
}