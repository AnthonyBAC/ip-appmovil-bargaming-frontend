package com.example.bargaming_grupo4.data

import com.example.bargaming_grupo4.model.LoginRequest
import com.example.bargaming_grupo4.network.RetrofitClient
import retrofit2.Response

class UserRepository {
    private val api = RetrofitClient.authService

    suspend fun getAllUsers(token: String) =
        api.getAllUsers("Bearer $token")
    }
