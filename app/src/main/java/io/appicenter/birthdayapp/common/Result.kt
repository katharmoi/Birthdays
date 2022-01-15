package io.appicenter.birthdayapp.common

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val err: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
