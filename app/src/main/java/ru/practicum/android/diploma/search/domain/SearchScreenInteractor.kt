package ru.practicum.android.diploma.search.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponse
import ru.practicum.android.diploma.network.domain.models.Vacancy

interface SearchScreenInteractor {

    suspend fun searchVacancy(text: String): ApiResult<VacancyResponse>
}
