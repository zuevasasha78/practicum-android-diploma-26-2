package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.search.domain.model.ApiResult
import ru.practicum.android.diploma.search.domain.model.requests.VacanciesFilter
import ru.practicum.android.diploma.search.domain.states.SearchScreenState

class SearchScreenInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository
) : SearchScreenInteractor {

    override suspend fun searchVacancy(filter: VacanciesFilter): SearchScreenState {
        val res = vacancyNetworkRepository.getVacancies(filter)

        return when (res) {
            is ApiResult.NoInternetConnection -> SearchScreenState.NoInternet
            is ApiResult.ServerError -> SearchScreenState.ServerError
            is ApiResult.NotFound -> SearchScreenState.NotFound
            is ApiResult.Success -> {
                SearchScreenState.Success(
                    amount = res.data.found,
                    lastPage = res.data.pages,
                    vacancyList = res.data.items
                )
            }
        }
    }
}
