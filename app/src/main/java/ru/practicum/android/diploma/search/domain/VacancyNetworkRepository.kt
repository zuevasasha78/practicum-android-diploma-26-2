package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.search.domain.model.ApiResult
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse
import ru.practicum.android.diploma.search.domain.model.requests.VacanciesFilter

interface VacancyNetworkRepository {

    suspend fun getAreas(): ApiResult<List<Location>>

    suspend fun getIndustries(): ApiResult<List<FilterIndustry>>

    suspend fun getVacancy(id: String): ApiResult<VacancyDetail>

    suspend fun getVacancies(vacanciesFilter: VacanciesFilter): ApiResult<VacancyResponse>
}
