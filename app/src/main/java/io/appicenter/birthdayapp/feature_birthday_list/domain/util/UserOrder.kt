package io.appicenter.birthdayapp.feature_birthday_list.domain.util

sealed class UserOrder(val orderType: OrderType) {
    class Name(orderType: OrderType) : UserOrder(orderType)
    class Date(orderType: OrderType) : UserOrder(orderType)
}
