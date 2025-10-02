package ru.practicum.android.diploma.vacancy.domain

import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.network.domain.VacancyRepository

class GetVacancyInteractor(private val repository: VacancyRepository) {

    suspend fun execute(vacancyId: String): ApiResult<VacancyDetail> {
        return repository.getVacancy(vacancyId)
    }
}
