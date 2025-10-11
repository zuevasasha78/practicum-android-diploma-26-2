package ru.practicum.android.diploma.network.data.impl

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.DiplomaApiService
import ru.practicum.android.diploma.network.data.NetworkClient
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilterDto
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustryDto
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetailDto
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponseDto
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository

class VacancyNetworkRepositoryImpl(
    private val networkClient: NetworkClient,
    private val diplomaApiService: DiplomaApiService
) : VacancyNetworkRepository {

    override suspend fun getAreas(): ApiResult<List<FilterArea>> {
        return networkClient.doRequest { diplomaApiService.getAreas() }
    }

    override suspend fun getIndustries(): ApiResult<List<FilterIndustryDto>> {
        return networkClient.doRequest { diplomaApiService.getIndustries() }
    }

    override suspend fun getVacancy(id: String): ApiResult<VacancyDetailDto> {
        return networkClient.doRequest { diplomaApiService.getVacancy(id) }
    }

    override suspend fun getVacancies(vacanciesFilterDto: VacanciesFilterDto): ApiResult<VacancyResponseDto> {
        val map = mutableMapOf<String, String>()
        vacanciesFilterDto.area?.let { map["area"] = it.toString() }
        vacanciesFilterDto.industry?.let { map["industry"] = it.toString() }
        vacanciesFilterDto.text?.let { map["text"] = it }
        vacanciesFilterDto.salary?.let { map["salary"] = it.toString() }
        vacanciesFilterDto.page?.let { map["page"] = it.toString() }
        vacanciesFilterDto.onlyWithSalary?.let { map["only_with_salary"] = it.toString() }
        return networkClient.doRequest { diplomaApiService.getVacancies(map) }
    }
}
