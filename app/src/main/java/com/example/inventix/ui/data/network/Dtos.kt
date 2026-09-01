package com.example.inventix.ui.data.network

import kotlinx.serialization.Serializable

// ---- Supabase Auth ----

@Serializable
data class LoginRequest(val email: String, val password: String)

@Serializable
data class SignUpMetadata(val full_name: String, val role: String, val store_name: String)

@Serializable
data class SignUpRequest(val email: String, val password: String, val data: SignUpMetadata)

@Serializable
data class RecoverRequest(val email: String)

@Serializable
data class EmptyResponse(val error: String? = null)

@Serializable
data class SupabaseUser(val id: String? = null, val email: String? = null)

@Serializable
data class AuthResponse(
    val access_token: String? = null,
    val token_type: String? = null,
    val expires_in: Int? = null,
    val user: SupabaseUser? = null,
    // Supabase returns this instead of a token when email confirmation is required
    val msg: String? = null
)

// ---- InventiX backend ----

@Serializable
data class ProductDto(
    val id: String? = null,
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val status: String, // "in_stock" | "low_stock" | "out_of_stock"
    val owner_id: String? = null
)

@Serializable
data class ProductCreateDto(
    val name: String,
    val category: String,
    val price: Double,
    val stock: Int
    // No status field — the backend computes it automatically from stock.
)

@Serializable
data class StockSegmentDto(val label: String, val value: Int, val percent: Double)

@Serializable
data class StockOverviewDto(val total: Int, val segments: List<StockSegmentDto>)
