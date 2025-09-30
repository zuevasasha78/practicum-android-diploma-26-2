package ru.practicum.android.diploma.network.data

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val code: Int) : ApiResult<T>()
    object NoInternetConnection : ApiResult<Nothing>()
}
