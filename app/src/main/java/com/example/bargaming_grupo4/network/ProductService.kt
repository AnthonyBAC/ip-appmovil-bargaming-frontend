package com.example.bargaming_grupo4.network

import com.example.bargaming_grupo4.model.Product
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("api/productos")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("api/productos/{id}")
    suspend fun getProductById(@Path("id") id: Long): Response<Product>

    @DELETE("api/productos/{id}")
    suspend fun deleteProductById(@Path("id") id: Long): Response<Product>
}