package ru.practicum.android.diploma.search.data.dto

sealed class ApiResultDto<out T> {
    data class Success<T>(val data: T) : ApiResultDto<T>()
    data class Error<T>(val code: Int) : ApiResultDto<T>()
    data object NoInternetConnection : ApiResultDto<Nothing>()
}
