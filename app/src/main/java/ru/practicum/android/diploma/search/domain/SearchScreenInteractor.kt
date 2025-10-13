package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.search.domain.models.SearchScreenState

interface SearchScreenInteractor {
    suspend fun searchVacancy(
        text: String,
        page: Int,
        industry: Int? = null,
        salary: String = "",
        onlyWithSalary: Boolean = false
    ): SearchScreenState
}
