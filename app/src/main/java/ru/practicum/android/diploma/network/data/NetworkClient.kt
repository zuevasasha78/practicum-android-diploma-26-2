package ru.practicum.android.diploma.network.data

import android.util.Log
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.App.Companion.getAppContext
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.utils.NetworkUtils

class NetworkClient {

    companion object {

        private const val BASE_URL = "https://practicum-diploma-8bc38133faba.herokuapp.com/"

        private const val HTTP_CODE_400 = 400
    }

    private val networkUtils = NetworkUtils(getAppContext())

    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.API_ACCESS_TOKEN}")
                .addHeader("Accept", "application/json")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    suspend fun <T> doRequest(serviceRequest: suspend () -> T): ApiResult<T> {
        if (networkUtils.isInternetAvailable() == false) {
            return ApiResult.NoInternetConnection
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = serviceRequest()
                ApiResult.Success(response)
            } catch (e: HttpException) {
                ApiResult.Error(e.code())
            } catch (e: JsonSyntaxException) {
                Log.e("NetworkClient", "JSON parsing error", e)
                ApiResult.Error(HTTP_CODE_400)
            }
        }
    }
}
