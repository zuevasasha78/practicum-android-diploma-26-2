package ru.practicum.android.diploma.search.domain

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.domain.models.VacancyResponse

interface SearchScreenInteractor {

    suspend fun searchVacancy(text: String): ApiResult<VacancyResponse>
}
