package ru.practicum.android.diploma.search.domain.models

import androidx.annotation.StringRes

sealed class PaginationState {
    data object Idle : PaginationState()
    data object Loading : PaginationState()
    data class Error(@StringRes val message: Int) : PaginationState()
}
