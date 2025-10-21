package ru.practicum.android.diploma.favourites.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

interface VacancyDbRepository {

    suspend fun addVacancy(vacancy: VacancyDetail): Boolean

    suspend fun deleteVacancy(vacancyId: String): Boolean

    fun getVacancies(): Flow<List<Vacancy>?>
    suspend fun isVacancyFavorite(vacancyId: String): Boolean

    suspend fun getVacancyById(vacancyId: String): VacancyDetail?
}
