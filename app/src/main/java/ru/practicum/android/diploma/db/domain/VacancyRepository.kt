package ru.practicum.android.diploma.db.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.data.entity.VacancyEntity

interface VacancyRepository {

    // todo заменить VacancyEntity на модель
    suspend fun addVacancy(vacancy: VacancyEntity)

    suspend fun deleteVacancy(vacancyId: String)

    // todo заменить VacancyEntity на модель
    suspend fun getVacancies(): Flow<List<VacancyEntity>?>
}
