package ru.practicum.android.diploma.db.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

interface VacancyDbRepository {

    suspend fun addVacancy(vacancy: VacancyDetail): Boolean

    suspend fun deleteVacancy(vacancyId: String): Boolean

    fun getVacancies(): Flow<List<VacancyDetail>?>
    suspend fun isVacancyFavorite(vacancyId: String): Boolean

    suspend fun getVacancyById(vacancyId: String): VacancyDetail?
}
