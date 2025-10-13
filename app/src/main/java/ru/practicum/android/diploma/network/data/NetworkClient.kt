package ru.practicum.android.diploma.network.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.utils.NetworkUtils

class NetworkClient(private val networkUtils: NetworkUtils) {

    suspend fun <T> doRequest(serviceRequest: suspend () -> T): ApiResultDto<T> {
        if (networkUtils.isInternetAvailable() == false) {
            return ApiResultDto.NoInternetConnection
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = serviceRequest()
                ApiResultDto.Success(response)
            } catch (e: HttpException) {
                ApiResultDto.Error(e.code())
            }
        }
    }
}
