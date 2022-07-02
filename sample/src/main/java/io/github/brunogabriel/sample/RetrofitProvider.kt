package io.github.brunogabriel.sample

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitProvider {
    private const val BASE_URL = "https://rickandmortyapi.com/api/"
    private lateinit var retrofit: Retrofit

    fun setup(context: Context) {
        val client = OkHttpClient.Builder()
            .writeTimeout(30L, TimeUnit.SECONDS)
            .readTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    fun <T> provideService(clazz: Class<T>): T = retrofit.create(clazz)
}