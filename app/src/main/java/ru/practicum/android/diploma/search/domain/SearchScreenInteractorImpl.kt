package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.search.domain.model.ApiResult
import ru.practicum.android.diploma.search.domain.model.requests.VacanciesFilter
import ru.practicum.android.diploma.search.presentation.models.Placeholder
import ru.practicum.android.diploma.search.presentation.models.SearchScreenState

class SearchScreenInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository
) : SearchScreenInteractor {

    override suspend fun searchVacancy(filter: VacanciesFilter): SearchScreenState {
        val res = vacancyNetworkRepository.getVacancies(filter)

        return when (res) {
            is ApiResult.NoInternetConnection -> SearchScreenState.Error(Placeholder.NoInternet)
            is ApiResult.ServerError -> SearchScreenState.Error(Placeholder.ServerError)
            is ApiResult.NotFound -> SearchScreenState.Error(Placeholder.NoResult)
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
