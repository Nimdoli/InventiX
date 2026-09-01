package com.example.inventix.ui.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/** Holds the current Supabase session token in memory. Set on successful
 * login/signup, cleared on logout. Read by the backend OkHttp interceptor
 * below to authenticate every request to our FastAPI backend. */
object TokenHolder {
    var accessToken: String? = null
}

object ApiConfig {
    // Supabase project (Auth only — the app never talks to Supabase's own
    // database REST API directly, just Auth; all business data goes through
    // our FastAPI backend, which itself talks to Postgres).
    const val SUPABASE_URL = "https://ykwflzybugkcnjvvgbfd.supabase.co/"
    const val SUPABASE_ANON_KEY = "sb_publishable_VS2SjxAYbEsYWhV7mTb5MA_LWmVMMpV"

    // 10.0.2.2 is the Android emulator's alias for the host machine's
    // localhost, where `uvicorn app.main:app` runs during development.
    const val BACKEND_BASE_URL = "http://10.0.2.2:8000/"
}

object NetworkModule {
    private val json = Json { ignoreUnknownKeys = true }
    private val jsonMediaType = "application/json".toMediaType()

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("apikey", ApiConfig.SUPABASE_ANON_KEY)
                .addHeader("Content-Type", "application/json")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(loggingInterceptor)
        .build()

    private val backendClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val builder = chain.request().newBuilder()
            TokenHolder.accessToken?.let { builder.addHeader("Authorization", "Bearer $it") }
            chain.proceed(builder.build())
        }
        .addInterceptor(loggingInterceptor)
        .build()

    val authApi: SupabaseAuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.SUPABASE_URL)
            .client(authClient)
            .addConverterFactory(json.asConverterFactory(jsonMediaType))
            .build()
            .create(SupabaseAuthApi::class.java)
    }

    val backendApi: BackendApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BACKEND_BASE_URL)
            .client(backendClient)
            .addConverterFactory(json.asConverterFactory(jsonMediaType))
            .build()
            .create(BackendApi::class.java)
    }
}
