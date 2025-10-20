package ru.practicum.android.diploma.network.domain

import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.network.domain.models.ApiResult
import ru.practicum.android.diploma.network.domain.models.FilterIndustry
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.network.domain.models.VacancyResponse
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter

interface VacancyNetworkRepository {

    suspend fun getAreas(): ApiResult<List<Location>>

    suspend fun getIndustries(): ApiResult<List<FilterIndustry>>

    suspend fun getVacancy(id: String): ApiResult<VacancyDetail>

    suspend fun getVacancies(vacanciesFilter: VacanciesFilter): ApiResult<VacancyResponse>
}
