package com.example.bargaming_grupo4.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bargaming_grupo4.data.ProductRepository
import com.example.bargaming_grupo4.model.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository = ProductRepository()
) : ViewModel() {

    // Lista de productos
    private val _productos = MutableStateFlow<List<Product>>(emptyList())
    val productos: StateFlow<List<Product>> = _productos

    // Producto seleccionado (para detalles)
    private val _productoSeleccionado = MutableStateFlow<Product?>(null)
    val productoSeleccionado: StateFlow<Product?> = _productoSeleccionado

    // Estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Error general
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun getAllProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getAllProducts()
                if (response.isSuccessful) {
                    _productos.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun getProductById(id: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = repository.getProductById(id)
                if (response.isSuccessful) {
                    val product = response.body()
                    _productoSeleccionado.value = product
                    println("Producto recibido: $product")
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Error API: Código = ${response.code()} | $errorBody")
                    _error.value = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }


    //Extra
    private val _extraLoading = MutableStateFlow(false)
    val extraLoading = _extraLoading.asStateFlow()
    private fun setExtraLoading(value: Boolean) {
        _extraLoading.value = value
    }

    fun mostrarLoaderInicial() {
        viewModelScope.launch {
            setExtraLoading(true)
            delay(1200)
            setExtraLoading(false)
        }
    }
}
