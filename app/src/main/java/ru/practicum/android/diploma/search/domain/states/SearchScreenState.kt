package ru.practicum.android.diploma.search.domain.states

import ru.practicum.android.diploma.search.domain.model.Vacancy

sealed class SearchScreenState {
    data object Init : SearchScreenState()
    data object Loading : SearchScreenState()
    data class Success(
        val amount: Int,
        val lastPage: Int,
        val vacancyList: List<Vacancy>,
        val paginationState: PaginationState = PaginationState.Idle,
    ) : SearchScreenState()

    data object NoInternet : SearchScreenState()
    data object ServerError : SearchScreenState()
    data object NotFound : SearchScreenState()
}
