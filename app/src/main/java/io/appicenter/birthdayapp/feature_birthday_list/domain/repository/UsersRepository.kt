package io.appicenter.birthdayapp.feature_birthday_list.domain.repository

import io.appicenter.birthdayapp.common.Result
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.User

/**
 * Facade to users data
 */
interface UsersRepository {

    suspend fun getUsers(): Result<List<User>>
}