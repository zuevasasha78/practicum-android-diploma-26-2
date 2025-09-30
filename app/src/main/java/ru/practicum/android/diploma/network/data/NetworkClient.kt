package ru.practicum.android.diploma.network.data

import android.util.Log
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.utils.NetworkUtils

class NetworkClient(private val networkUtils: NetworkUtils) {

    companion object {
        private const val HTTP_CODE_400 = 400
    }

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
