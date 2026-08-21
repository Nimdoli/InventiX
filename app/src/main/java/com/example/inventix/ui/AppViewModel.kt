package com.example.inventix.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.inventix.ui.data.UserRole

class AppViewModel : ViewModel() {

    var role by mutableStateOf<UserRole?>(null)
        private set

    var hasProducts by mutableStateOf(true)
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
    }
}
