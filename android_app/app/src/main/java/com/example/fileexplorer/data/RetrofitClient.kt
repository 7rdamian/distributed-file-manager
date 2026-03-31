package com.example.fileexplorer.data

import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.Tasks
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            try {
                val tokenResult = Tasks.await(user.getIdToken(false))
                val token = tokenResult.token

                if (token != null) {
                    android.util.Log.d("RetrofitAuth", "Token found and attached!")
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                } else {
                    android.util.Log.e("RetrofitAuth", "User logged in, but token is NULL")
                }
            } catch (e: Exception) {
                android.util.Log.e("RetrofitAuth", "Error fetching token: ${e.message}")
            }
        } else {
            android.util.Log.e("RetrofitAuth", "No user logged in. Sending request without token.")
        }

        return chain.proceed(requestBuilder.build())
    }
}

object RetrofitClient {
    private const val BASE_URL = "https://api.personalnas.online/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor())
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()) 
            .build()
            .create(ApiService::class.java)
    }
}