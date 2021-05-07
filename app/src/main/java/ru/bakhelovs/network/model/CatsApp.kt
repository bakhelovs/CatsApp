package ru.bakhelovs.network.model

import android.app.Application
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import ru.bakhelovs.network.data.remote.retrofit.CatsApiService


class CatsApp : Application() {


    private class CatsApiHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val originalHttpUrl = originalRequest.url
            val request = originalRequest.newBuilder()
                .url(originalHttpUrl)
                .addHeader(API_KEY_HEADER, apiKey)
                .build()

            return chain.proceed(request)
        }
    }

    @ExperimentalSerializationApi
    object RetrofitModule {
        private val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(CatsApiHeaderInterceptor())
            .build()

        private val json = Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        }

        @ExperimentalSerializationApi
        private val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        val catsApi: CatsApiService = retrofit.create(CatsApiService::class.java)
    }


    companion object {
        private const val API_KEY_HEADER = "x-api-key"
        const val apiKey = "753009e5-1ad5-44dc-9cc0-ae43b4c6f8ce"

    }
}