package io.appicenter.birthdayapp.feature_birthday_list.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}
