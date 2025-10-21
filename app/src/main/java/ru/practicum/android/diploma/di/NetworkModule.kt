package ru.practicum.android.diploma.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.search.data.network.DiplomaApiService
import ru.practicum.android.diploma.search.data.network.NetworkClient

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${BuildConfig.API_ACCESS_TOKEN}")
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    single<DiplomaApiService> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DiplomaApiService::class.java)
    }

    single<NetworkClient> { NetworkClient(get()) }
}
