package ru.practicum.android.diploma.network.domain.models

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error<T>(val code: Int) : ApiResult<T>()
    data object NoInternetConnection : ApiResult<Nothing>()
}
