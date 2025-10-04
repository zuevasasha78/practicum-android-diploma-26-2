package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilter
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository

class SearchScreenInteractorImpl(private val vacancyNetworkRepository: VacancyNetworkRepository): SearchScreenInteractor {
    override suspend fun searchVacancy(text: String): ApiResult<VacancyResponse> {
        return vacancyNetworkRepository.getVacancies(
            VacanciesFilter(text = text)
        )
    }
}
