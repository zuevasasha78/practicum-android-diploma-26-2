package ru.practicum.android.diploma.network.data.impl

import ru.practicum.android.diploma.network.data.DiplomaApiService
import ru.practicum.android.diploma.network.data.NetworkClient
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultFilterArea
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultFilterIndustries
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultVacancyDetail
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultVacancyResponse
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.ApiResult
import ru.practicum.android.diploma.network.domain.models.FilterArea
import ru.practicum.android.diploma.network.domain.models.FilterIndustry
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.network.domain.models.VacancyResponse
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter

class VacancyNetworkRepositoryImpl(
    private val networkClient: NetworkClient,
    private val diplomaApiService: DiplomaApiService
) : VacancyNetworkRepository {

    override suspend fun getAreas(): ApiResult<List<FilterArea>> {
        return networkClient.doRequest { diplomaApiService.getAreas() }.convertToApiResultFilterArea()
    }

    override suspend fun getIndustries(): ApiResult<List<FilterIndustry>> {
        return networkClient.doRequest { diplomaApiService.getIndustries() }.convertToApiResultFilterIndustries()
    }

    override suspend fun getVacancy(id: String): ApiResult<VacancyDetail> {
        return networkClient.doRequest { diplomaApiService.getVacancy(id) }.convertToApiResultVacancyDetail()
    }

    override suspend fun getVacancies(vacanciesFilter: VacanciesFilter): ApiResult<VacancyResponse> {
        val map = mutableMapOf<String, String>()
        vacanciesFilter.area?.let { map["area"] = it.toString() }
        vacanciesFilter.industry?.let { map["industry"] = it.toString() }
        vacanciesFilter.text?.let { map["text"] = it }
        vacanciesFilter.salary?.let { map["salary"] = it.toString() }
        vacanciesFilter.page?.let { map["page"] = it.toString() }
        vacanciesFilter.onlyWithSalary?.let { map["only_with_salary"] = it.toString() }
        return networkClient.doRequest { diplomaApiService.getVacancies(map) }.convertToApiResultVacancyResponse()
    }
}
