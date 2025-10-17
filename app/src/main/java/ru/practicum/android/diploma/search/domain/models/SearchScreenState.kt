package ru.practicum.android.diploma.search.domain.models

import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.models.Placeholder

sealed class SearchScreenState {
    data object Init : SearchScreenState()
    data object Loading : SearchScreenState()
    data class Success(
        val amount: Int,
        val lastPage: Int,
        val vacancyList: List<Vacancy>,
        val paginationState: PaginationState = PaginationState.Idle,
    ) : SearchScreenState()

    data class Error(val placeholder: Placeholder) : SearchScreenState()
}
