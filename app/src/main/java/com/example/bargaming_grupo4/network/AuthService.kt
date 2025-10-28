package com.example.bargaming_grupo4.network

import com.example.bargaming_grupo4.model.LoginRequest
import com.example.bargaming_grupo4.model.LoginResponse
import com.example.bargaming_grupo4.model.RegisterRequest
import com.example.bargaming_grupo4.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/auth/register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<Void>

    @GET("/api/admin/users")
    suspend fun getAllUsers(@Header("Authorization") token: String): Response<List<Usuario>>
}