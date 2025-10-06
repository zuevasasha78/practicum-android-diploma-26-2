package ru.practicum.android.diploma.search.domain

interface SearchScreenInteractor {

    suspend fun searchVacancy(text: String, page: Int): SearchScreenState
}
