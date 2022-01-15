package io.appicenter.birthdayapp.feature_birthday_list.common

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val err: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
