package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultVacancyResponse
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToVacanciesFilterDto
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter
import ru.practicum.android.diploma.network.domain.models.ApiResult
import ru.practicum.android.diploma.search.domain.models.SearchScreenState
import ru.practicum.android.diploma.search.presentation.models.Placeholder

class SearchScreenInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository
) : SearchScreenInteractor {

    override suspend fun searchVacancy(filter: VacanciesFilter): SearchScreenState {
        val res = vacancyNetworkRepository.getVacancies(
            filter.convertToVacanciesFilterDto()
        ).convertToApiResultVacancyResponse()

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
