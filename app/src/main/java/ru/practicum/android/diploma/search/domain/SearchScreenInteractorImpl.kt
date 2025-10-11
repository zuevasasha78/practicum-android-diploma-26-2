package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultVacancyResponse
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToVacanciesFilterDto
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter
import ru.practicum.android.diploma.search.domain.models.SearchScreenState
import ru.practicum.android.diploma.search.presentation.models.Placeholder

class SearchScreenInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository
) : SearchScreenInteractor {

    override suspend fun searchVacancy(text: String, page: Int): SearchScreenState {
        val res = vacancyNetworkRepository.getVacancies(
            VacanciesFilter(text = text, page = page).convertToVacanciesFilterDto()
        ).convertToApiResultVacancyResponse()
        return when (res) {
            is ApiResult.NoInternetConnection -> SearchScreenState.Error(Placeholder.NoInternet)
            is ApiResult.Error -> SearchScreenState.Error(Placeholder.ServerError)
            is ApiResult.Success -> {
                if (res.data.found == 0) {
                    SearchScreenState.Error(Placeholder.NoResult)
                } else {
                    SearchScreenState.Success(
                        amount = res.data.found,
                        lastPage = res.data.pages,
                        vacancyList = res.data.items
                    )
                }
            }
        }
    }
}
