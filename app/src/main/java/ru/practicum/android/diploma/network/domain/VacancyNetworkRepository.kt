package ru.practicum.android.diploma.network.domain

import ru.practicum.android.diploma.network.data.ApiResultDto
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilterDto
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustryDto
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetailDto
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponseDto

interface VacancyNetworkRepository {

    suspend fun getAreas(): ApiResultDto<List<FilterArea>>

    suspend fun getIndustries(): ApiResultDto<List<FilterIndustryDto>>

    suspend fun getVacancy(id: String): ApiResultDto<VacancyDetailDto>

    suspend fun getVacancies(vacanciesFilterDto: VacanciesFilterDto): ApiResultDto<VacancyResponseDto>
}
