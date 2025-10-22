package ru.practicum.android.diploma.search.domain.states

sealed interface PaginationState {
    data object Idle : PaginationState
    data object Loading : PaginationState
    data object Error : PaginationState
    data object NoInternet : PaginationState
}
