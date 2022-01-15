package io.appicenter.birthdayapp.feature_birthday_list.data.remote

import io.appicenter.birthdayapp.feature_birthday_list.data.remote.model.ApiResponse
import io.appicenter.birthdayapp.feature_birthday_list.data.util.NetworkConfig

import retrofit2.http.GET

interface UsersApi {

    /**
     * Get users from remote
     */
    @GET(NetworkConfig.USERS_ENDPOINT)
    suspend fun getUsers(): ApiResponse
}