package ru.practicum.android.diploma.search.data

import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.search.data.dto.converter.VacancyNetworkConvertor.convertToApiResultFilterArea
import ru.practicum.android.diploma.search.data.dto.converter.VacancyNetworkConvertor.convertToApiResultFilterIndustries
import ru.practicum.android.diploma.search.data.dto.converter.VacancyNetworkConvertor.convertToApiResultVacancyDetail
import ru.practicum.android.diploma.search.data.dto.converter.VacancyNetworkConvertor.convertToApiResultVacancyResponse
import ru.practicum.android.diploma.search.data.network.DiplomaApiService
import ru.practicum.android.diploma.search.data.network.NetworkClient
import ru.practicum.android.diploma.search.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.search.domain.model.ApiResult
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse
import ru.practicum.android.diploma.search.domain.model.requests.VacanciesFilter

class VacancyNetworkRepositoryImpl(
    private val networkClient: NetworkClient,
    private val diplomaApiService: DiplomaApiService
) : VacancyNetworkRepository {

    override suspend fun getAreas(): ApiResult<List<Location>> {
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
