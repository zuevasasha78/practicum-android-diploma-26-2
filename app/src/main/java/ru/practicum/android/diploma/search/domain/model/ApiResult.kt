package ru.practicum.android.diploma.search.domain.model

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data object NotFound : ApiResult<Nothing>()
    data object ServerError : ApiResult<Nothing>()
    data object NoInternetConnection : ApiResult<Nothing>()
}
