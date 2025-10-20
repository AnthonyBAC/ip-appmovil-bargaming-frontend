package com.example.bargaming_grupo4.network

import com.example.bargaming_grupo4.model.LoginRequest
import com.example.bargaming_grupo4.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}