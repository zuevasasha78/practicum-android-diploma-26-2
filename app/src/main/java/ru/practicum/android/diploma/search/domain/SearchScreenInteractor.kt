package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter
import ru.practicum.android.diploma.search.domain.models.SearchScreenState

interface SearchScreenInteractor {
    suspend fun searchVacancy(filter: VacanciesFilter): SearchScreenState
}
