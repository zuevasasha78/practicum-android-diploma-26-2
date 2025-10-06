package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.search.presentation.models.SearchPlaceholder

sealed class SearchScreenState {
    data object Init : SearchScreenState()
    data object Loading : SearchScreenState()
    data class Success(
        val amount: Int,
        val lastPage: Int,
        val vacancyList: List<Vacancy>
    ) : SearchScreenState()

    data class Error(val placeholder: SearchPlaceholder) : SearchScreenState()
}
