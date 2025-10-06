package ru.practicum.android.diploma.network.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.utils.NetworkUtils

class NetworkClient(private val networkUtils: NetworkUtils) {

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
            }
        }
    }
}
