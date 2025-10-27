package com.example.bargaming_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bargaming_grupo4.data.ProductRepository
import com.example.bargaming_grupo4.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Product>>(emptyList())
    val productos: StateFlow<List<Product>> = _productos

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getAllProducts() {
        viewModelScope.launch {
            try {
                val response = repository.getAllProducts()
                if (response.isSuccessful) {
                    _productos.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }

    private val _productoSeleccionado = MutableStateFlow<Product?>(null)
    val productoSeleccionado: StateFlow<Product?> = _productoSeleccionado

    fun getProductById(id: Long) {
        viewModelScope.launch {
            try {
                val response = repository.getProductById(id)
                if (response.isSuccessful) {
                    println("Producto recibido: ${response.body()}")
                    _productoSeleccionado.value = response.body()
                } else {
                    println("Error API: Código = ${response.code()} | ${response.errorBody()?.string()}")
                    _error.value = "Error ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage
            }
        }
    }

}