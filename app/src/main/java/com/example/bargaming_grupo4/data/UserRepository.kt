package com.example.bargaming_grupo4.data

import com.example.bargaming_grupo4.network.RetrofitClient

class UserRepository {
    private val api = RetrofitClient.authService

    suspend fun getAllUsers(token: String) =
        api.getAllUsers("Bearer $token")
}
