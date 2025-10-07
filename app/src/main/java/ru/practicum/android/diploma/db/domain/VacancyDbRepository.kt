package ru.practicum.android.diploma.db.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.network.domain.models.Vacancy

interface VacancyDbRepository {

    // todo заменить VacancyEntity на модель
    suspend fun addVacancy(vacancy: VacancyEntity)

    suspend fun deleteVacancy(vacancyId: String)

    fun getVacancies(): Flow<List<Vacancy>?>
}
