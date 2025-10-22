package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.search.domain.model.requests.VacanciesFilter
import ru.practicum.android.diploma.search.domain.states.SearchScreenState

interface SearchScreenInteractor {
    suspend fun searchVacancy(filter: VacanciesFilter): SearchScreenState
}
