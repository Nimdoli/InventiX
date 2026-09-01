package com.example.inventix.ui.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SupabaseAuthApi {
    @POST("auth/v1/token?grant_type=password")
    suspend fun login(@Body body: LoginRequest): AuthResponse

    @POST("auth/v1/signup")
    suspend fun signUp(@Body body: SignUpRequest): AuthResponse

    @POST("auth/v1/recover")
    suspend fun recoverPassword(@Body body: RecoverRequest): EmptyResponse
}

interface BackendApi {
    @GET("product")
    suspend fun listProducts(@Query("category") category: String? = null): List<ProductDto>

    @POST("product")
    suspend fun createProduct(@Body body: ProductCreateDto): ProductDto
}
