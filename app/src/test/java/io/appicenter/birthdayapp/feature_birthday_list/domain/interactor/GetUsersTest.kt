package io.appicenter.birthdayapp.feature_birthday_list.domain.interactor

import com.google.common.truth.Truth.assertThat
import io.appicenter.birthdayapp.common.Result
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.Birthday
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.Name
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.User
import io.appicenter.birthdayapp.feature_birthday_list.domain.repository.UsersRepository
import io.appicenter.birthdayapp.feature_birthday_list.domain.util.OrderType
import io.appicenter.birthdayapp.feature_birthday_list.domain.util.UserOrder
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.text.SimpleDateFormat
import java.util.*


class GetUsersTest {

    private lateinit var getUsers: GetUsers

    private val repo: UsersRepository = Mockito.mock(UsersRepository::class.java)

    private val mockUsers = mutableListOf<User>()

    @Before
    fun setUp() {

        getUsers = GetUsers(repo)

        ('a'..'z').forEachIndexed { index, c ->
            mockUsers.add(
                User(
                    Name(
                        title = c.toString(),
                        first = c.toString(),
                        last = c.toString()
                    ),
                    Birthday(
                        date = "1980-08-${index}T12:09",
                        age = index
                    )
                )
            )
        }

        mockUsers.shuffle()


    }

    @Test
    fun `When order users by birthday ascending, then it should return correct order`() =
        runBlocking {

            Mockito.`when`(repo.getUsers()).thenReturn(Result.Success(mockUsers))

            val usersResult = getUsers(UserOrder.Date(OrderType.Ascending)).toList()
            val users = (usersResult[1] as Result.Success).data

            for (i in 0..users.size - 2) {
                assertThat(parseDateString(users[i])).isLessThan(parseDateString(users[i + 1]))
            }

        }

    @Test
    fun `When order users by birthday descending, then it should return correct order`() =
        runBlocking {

            Mockito.`when`(repo.getUsers()).thenReturn(Result.Success(mockUsers))

            val usersResult = getUsers(UserOrder.Date(OrderType.Descending)).toList()
            val users = (usersResult[1] as Result.Success).data

            for (i in 0..users.size - 2) {
                assertThat(parseDateString(users[i])).isGreaterThan(parseDateString(users[i + 1]))
            }

        }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    private fun parseDateString(user: User): Date {
        return SimpleDateFormat("yyyy-MM-dd").parse(
            user.dateOfBirth.date.split("T").first()
        )
    }

}