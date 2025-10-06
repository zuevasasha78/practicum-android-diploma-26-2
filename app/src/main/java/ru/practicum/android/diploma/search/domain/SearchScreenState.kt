package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.domain.models.Vacancy

sealed class SearchScreenState {
    data object Init : SearchScreenState()
    data object Loading : SearchScreenState()
    data class Success(val amount: Int, val vacancyList: List<Vacancy>) : SearchScreenState()
    data object NoInternet : SearchScreenState()
    data object Error : SearchScreenState()
}
