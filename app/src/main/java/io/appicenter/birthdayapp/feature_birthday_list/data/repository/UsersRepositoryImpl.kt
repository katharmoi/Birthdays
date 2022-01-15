package io.appicenter.birthdayapp.feature_birthday_list.data.repository

import io.appicenter.birthdayapp.feature_birthday_list.data.remote.UsersApi
import io.appicenter.birthdayapp.feature_birthday_list.domain.model.User
import io.appicenter.birthdayapp.feature_birthday_list.domain.repository.UsersRepository
import io.appicenter.birthdayapp.common.Result
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
) : UsersRepository {

    override suspend fun getUsers(): Result<List<User>> {
        return try {
            val users = usersApi.getUsers().results

            Result.Success(users)
        } catch (e: HttpException) {
            Result.Error(Exception(e.localizedMessage ?:"Unknown Error"))
        } catch (e: IOException) {
            Result.Error(Exception("Check your internet connection"))
        }catch (e:Exception){
            Result.Error(Exception("Unknown:("))
        }

    }
}