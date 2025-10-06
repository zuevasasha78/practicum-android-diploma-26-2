package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultVacancyResponse
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToVacanciesFilterDto
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter

class SearchScreenInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository
) : SearchScreenInteractor {
    override suspend fun searchVacancy(text: String): SearchScreenState {
        val res = vacancyNetworkRepository.getVacancies(
            VacanciesFilter(text = text).convertToVacanciesFilterDto()
        ).convertToApiResultVacancyResponse()
        return when (res) {
            is ApiResult.NoInternetConnection -> SearchScreenState.NoInternet
            is ApiResult.Error -> SearchScreenState.Error
            is ApiResult.Success -> SearchScreenState.Success(res.data.found, res.data.items)
        }
    }
}
