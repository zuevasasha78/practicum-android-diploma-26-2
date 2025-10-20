package ru.practicum.android.diploma.network.domain

import ru.practicum.android.diploma.network.domain.models.ApiResult
import ru.practicum.android.diploma.network.domain.models.FilterArea
import ru.practicum.android.diploma.network.domain.models.FilterIndustry
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.network.domain.models.VacancyResponse
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter

interface VacancyNetworkRepository {

    suspend fun getAreas(): ApiResult<List<FilterArea>>

    suspend fun getIndustries(): ApiResult<List<FilterIndustry>>

    suspend fun getVacancy(id: String): ApiResult<VacancyDetail>

    suspend fun getVacancies(vacanciesFilter: VacanciesFilter): ApiResult<VacancyResponse>
}
