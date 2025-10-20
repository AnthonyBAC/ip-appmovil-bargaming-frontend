package com.example.bargaming_grupo4.model

data class LoginRequest(
    val email: String,
    val password: String
)
data class LoginResponse(
    val token: String,
    val email: String,
    val role: String
)