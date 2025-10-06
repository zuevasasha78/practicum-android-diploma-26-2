package ru.practicum.android.diploma.network.domain

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilterDto
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustry
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetailDto
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponseDto

interface VacancyNetworkRepository {

    suspend fun getAreas(): ApiResult<List<FilterArea>>

    suspend fun getIndustries(): ApiResult<List<FilterIndustry>>

    suspend fun getVacancy(id: String): ApiResult<VacancyDetailDto>

    suspend fun getVacancies(vacanciesFilterDto: VacanciesFilterDto): ApiResult<VacancyResponseDto>
}
