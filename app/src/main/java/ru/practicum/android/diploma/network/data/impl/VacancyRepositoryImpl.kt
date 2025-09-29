package ru.practicum.android.diploma.network.data.impl

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.DiplomaApiService
import ru.practicum.android.diploma.network.data.NetworkClient
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilter
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustry
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.network.domain.VacancyRepository

class VacancyRepositoryImpl : VacancyRepository {

    val networkClient = NetworkClient()
    val diplomaApiService: DiplomaApiService = networkClient.retrofit.create(DiplomaApiService::class.java)

    override suspend fun getAreas(): ApiResult<List<FilterArea>> {
        return networkClient.doRequest { diplomaApiService.getAreas() }
    }

    override suspend fun getIndustries(): ApiResult<List<FilterIndustry>> {
        return networkClient.doRequest { diplomaApiService.getIndustries() }
    }

    override suspend fun getVacancy(id: String): ApiResult<VacancyDetail> {
        return networkClient.doRequest { diplomaApiService.getVacancy(id) }
    }

    override suspend fun getVacancies(vacanciesFilter: VacanciesFilter): ApiResult<VacancyResponse> {
        val map = mutableMapOf<String, String>()
        vacanciesFilter.area?.let { map["area"] = it.toString() }
        vacanciesFilter.industry?.let { map["industry"] = it.toString() }
        vacanciesFilter.text?.let { map["text"] = it.toString() }
        vacanciesFilter.salary?.let { map["salary"] = it.toString() }
        vacanciesFilter.page?.let { map["page"] = it.toString() }
        vacanciesFilter.onlyWithSalary?.let { map["only_with_salary"] = it.toString() }
        return networkClient.doRequest { diplomaApiService.getVacancies(map) }
    }
}
