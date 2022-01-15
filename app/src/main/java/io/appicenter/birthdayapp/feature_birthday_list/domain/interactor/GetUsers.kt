package io.appicenter.birthdayapp.feature_birthday_list.domain.interactor

import android.annotation.SuppressLint
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.User
import io.appicenter.birthdayapp.feature_birthday_list.domain.repository.UsersRepository
import io.appicenter.birthdayapp.feature_birthday_list.domain.util.OrderType
import io.appicenter.birthdayapp.feature_birthday_list.domain.util.UserOrder
import io.appicenter.birthdayapp.common.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import javax.inject.Inject

class GetUsers @Inject constructor(
    private val usersRepository: UsersRepository
) {

    @SuppressLint("SimpleDateFormat")
    operator fun invoke(userOrder: UserOrder = UserOrder.Date(OrderType.Descending)): Flow<Result<List<User>>> =
        flow {

            emit(Result.Loading)

            val usersResult = usersRepository.getUsers()

            if (usersResult is Result.Success) {

                val users: List<User>

                when (userOrder.orderType) {
                    OrderType.Ascending -> {
                        users = when (userOrder) {
                            is UserOrder.Date -> {
                                usersResult.data.sortedBy {
                                    SimpleDateFormat("yyyy-MM-dd").parse(
                                        it.dateOfBirth.date.split("T").first()
                                    )
                                }
                            }
                            is UserOrder.Name -> {
                                usersResult.data.sortedBy { it.name.first }
                            }
                        }
                    }
                    OrderType.Descending -> {
                        users = when (userOrder) {
                            is UserOrder.Date -> usersResult.data.sortedByDescending {
                                SimpleDateFormat("yyyy-MM-dd").parse(
                                    it.dateOfBirth.date.split("T").first()
                                )
                            }
                            is UserOrder.Name -> usersResult.data.sortedByDescending { it.name.first }
                        }
                    }
                }
                emit(Result.Success(data = users))
            }

            if (usersResult is Result.Error) {
                emit(Result.Error(usersResult.err))
            }
        }
}