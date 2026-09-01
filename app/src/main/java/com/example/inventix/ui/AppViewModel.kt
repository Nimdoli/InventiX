package com.example.inventix.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inventix.ui.data.BadgeType
import com.example.inventix.ui.data.Product
import com.example.inventix.ui.data.UserRole
import com.example.inventix.ui.data.network.LoginRequest
import com.example.inventix.ui.data.network.NetworkModule
import com.example.inventix.ui.data.network.ProductCreateDto
import com.example.inventix.ui.data.network.ProductDto
import com.example.inventix.ui.data.network.RecoverRequest
import com.example.inventix.ui.data.network.SignUpMetadata
import com.example.inventix.ui.data.network.SignUpRequest
import com.example.inventix.ui.data.network.TokenHolder
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

    var role by mutableStateOf<UserRole?>(null)
        private set

    var hasProducts by mutableStateOf(false)
        private set

    var hasOrders by mutableStateOf(false)
        private set

    var hasDeliveries by mutableStateOf(false)
        private set

    // ---- Auth state ----
    var authLoading by mutableStateOf(false)
        private set

    var authError by mutableStateOf<String?>(null)
        private set

    // ---- Products state (loaded from the real backend) ----
    var products by mutableStateOf<List<Product>>(emptyList())
        private set

    var productsLoading by mutableStateOf(false)
        private set

    var productsError by mutableStateOf<String?>(null)
        private set

    fun chooseRole(newRole: UserRole) {
        role = newRole
    }

    fun addFirstProduct() {
        hasProducts = true
    }

    fun logout() {
        role = null
        hasProducts = false
        hasOrders = false
        hasDeliveries = false
        TokenHolder.accessToken = null
        products = emptyList()
    }

    fun login(email: String, password: String, onResult: (Boolean) -> Unit) {
        authError = null
        authLoading = true
        viewModelScope.launch {
            try {
                val response = NetworkModule.authApi.login(LoginRequest(email, password))
                val token = response.access_token
                if (token != null) {
                    TokenHolder.accessToken = token
                    loadProducts()
                    onResult(true)
                } else {
                    authError = response.msg ?: "Login failed. Check your email and password."
                    onResult(false)
                }
            } catch (e: Exception) {
                authError = "Couldn't reach the server: ${e.message}"
                onResult(false)
            } finally {
                authLoading = false
            }
        }
    }

    fun register(
        fullName: String,
        storeName: String,
        email: String,
        password: String,
        onResult: (Boolean) -> Unit
    ) {
        authError = null
        authLoading = true
        val roleValue = if (role == UserRole.SUPPLIER) "supplier" else "customer"
        viewModelScope.launch {
            try {
                val response = NetworkModule.authApi.signUp(
                    SignUpRequest(
                        email = email,
                        password = password,
                        data = SignUpMetadata(full_name = fullName, role = roleValue, store_name = storeName)
                    )
                )
                val token = response.access_token
                if (token != null) {
                    TokenHolder.accessToken = token
                    onResult(true)
                } else {
                    // Supabase returns no access_token when email confirmation is required —
                    // that's still a successful signup, just not an immediate session.
                    authError = null
                    onResult(true)
                }
            } catch (e: Exception) {
                authError = "Couldn't create account: ${e.message}"
                onResult(false)
            } finally {
                authLoading = false
            }
        }
    }

    fun sendPasswordReset(email: String, onResult: (Boolean) -> Unit) {
        authError = null
        authLoading = true
        viewModelScope.launch {
            try {
                NetworkModule.authApi.recoverPassword(RecoverRequest(email))
                onResult(true)
            } catch (e: Exception) {
                authError = "Couldn't send reset email: ${e.message}"
                onResult(false)
            } finally {
                authLoading = false
            }
        }
    }

    fun createProduct(name: String, category: String, price: Double, stock: Int, status: BadgeType) {
        val statusValue = when (status) {
            BadgeType.LOW_STOCK -> "low_stock"
            BadgeType.OUT_OF_STOCK -> "out_of_stock"
            else -> "in_stock"
        }
        viewModelScope.launch {
            try {
                NetworkModule.backendApi.createProduct(
                    ProductCreateDto(name = name, category = category, price = price, stock = stock, status = statusValue)
                )
                loadProducts()
            } catch (e: Exception) {
                productsError = "Couldn't add product: ${e.message}"
            }
        }
    }

    fun loadProducts() {
        productsError = null
        productsLoading = true
        viewModelScope.launch {
            try {
                val dtos = NetworkModule.backendApi.listProducts()
                products = dtos.map { it.toProduct() }
                hasProducts = products.isNotEmpty()
            } catch (e: Exception) {
                productsError = "Couldn't load products: ${e.message}"
            } finally {
                productsLoading = false
            }
        }
    }
}

private fun ProductDto.toProduct(): Product {
    val status = when (status) {
        "in_stock" -> BadgeType.IN_STOCK
        "low_stock" -> BadgeType.LOW_STOCK
        "out_of_stock" -> BadgeType.OUT_OF_STOCK
        else -> BadgeType.IN_STOCK
    }
    return Product(
        name = name,
        category = category,
        price = "LKR %,.2f".format(price),
        stock = stock,
        status = status
    )
}
