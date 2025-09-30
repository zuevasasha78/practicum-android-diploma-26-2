package ru.practicum.android.diploma.network.domain

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilter
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustry
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponse

interface VacancyRepository {

    suspend fun getAreas(): ApiResult<List<FilterArea>>

    suspend fun getIndustries(): ApiResult<List<FilterIndustry>>

    suspend fun getVacancy(id: String): ApiResult<VacancyDetail>

    suspend fun getVacancies(vacanciesFilter: VacanciesFilter): ApiResult<VacancyResponse>
}
