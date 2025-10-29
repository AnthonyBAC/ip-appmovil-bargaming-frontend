package com.example.bargaming_grupo4.data

import com.example.bargaming_grupo4.model.Product
import com.example.bargaming_grupo4.network.RetrofitClient
import retrofit2.Response

class ProductRepository {
    private val api = RetrofitClient.productService

    suspend fun getAllProducts(): Response<List<Product>> {
        return api.getAllProducts()
    }

    suspend fun getProductById(id: Long): Response<Product> {
        return api.getProductById(id)
    }


}