package com.example.inventix.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventix.ui.data.Product
import com.example.inventix.ui.data.UserRole

class AppViewModel : ViewModel() {

    var role by mutableStateOf<UserRole?>(null)
        private set

    val products = mutableStateListOf<Product>()

    var hasOrders by mutableStateOf(false)
        private set

    var hasDeliveries by mutableStateOf(false)
        private set

    fun chooseRole(newRole: UserRole) {
        role = newRole
    }

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun logout() {
        role = null
        products.clear()
        hasOrders = false
        hasDeliveries = false
    }
}
