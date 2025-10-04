package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.VacancyResponse
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter
import ru.practicum.android.diploma.utils.Utils.map

class SearchScreenInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository
) : SearchScreenInteractor {
    override suspend fun searchVacancy(text: String): ApiResult<VacancyResponse> {
        return vacancyNetworkRepository.getVacancies(
            VacanciesFilter(text = text).map()
        ).map()
    }
}
