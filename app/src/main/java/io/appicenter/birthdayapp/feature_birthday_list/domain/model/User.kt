package io.appicenter.birthdayapp.feature_birthday_list.domain.model

import com.google.gson.annotations.SerializedName

data class User(val name: Name, @SerializedName("dob") val dateOfBirth: Birthday)
