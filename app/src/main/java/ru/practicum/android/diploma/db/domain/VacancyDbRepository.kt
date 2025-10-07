package ru.practicum.android.diploma.db.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

interface VacancyDbRepository {

    // todo заменить VacancyEntity на модель
    suspend fun addVacancy(vacancy: VacancyDetail)

    suspend fun deleteVacancy(vacancyId: String)

    fun getVacancies(): Flow<List<Vacancy>?>

    suspend fun getVacancyById(vacancyId: String): VacancyDetail?
}
