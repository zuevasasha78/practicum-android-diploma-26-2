package ru.practicum.android.diploma.network.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilter
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustry
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponse

class NetworkClientImpl : NetworkClient {

    companion object {

        private const val BASE_URL = "https://practicum-diploma-8bc38133faba.herokuapp.com/"

        private const val HTTP_CODE_400 = 400
    }

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

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val diplomaApiService: DiplomaApiService = retrofit.create(DiplomaApiService::class.java)

    override suspend fun getAreas(): ApiResult<List<FilterArea>> {
        return doRequest { diplomaApiService.getAreas() }
    }

    override suspend fun getIndustries(): ApiResult<List<FilterIndustry>> {
        return doRequest { diplomaApiService.getIndustries() }
    }

    override suspend fun getVacancy(id: String): ApiResult<VacancyDetail> {
        return doRequest { diplomaApiService.getVacancy(id) }
    }

    override suspend fun getVacancies(vacanciesFilter: VacanciesFilter): ApiResult<VacancyResponse> {
        val map = mutableMapOf<String, String>()
        vacanciesFilter.area?.let { map["area"] = it.toString() }
        vacanciesFilter.industry?.let { map["industry"] = it.toString() }
        vacanciesFilter.text?.let { map["text"] = it.toString() }
        vacanciesFilter.salary?.let { map["salary"] = it.toString() }
        vacanciesFilter.page?.let { map["page"] = it.toString() }
        vacanciesFilter.onlyWithSalary?.let { map["only_with_salary"] = it.toString() }
        return doRequest { diplomaApiService.getVacancies(map) }
    }

    private suspend fun <T> doRequest(serviceRequest: suspend () -> T): ApiResult<T> {
        if (isConnected() == false) {
            return ApiResult.NoInternetConnection
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = serviceRequest()
                ApiResult.Success(response)
            } catch (e: HttpException) {
                ApiResult.Error(e.code())
            } catch (e: JsonSyntaxException) {
                ApiResult.Error(HTTP_CODE_400)
            }
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities != null &&
            (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    }
}
