package ru.practicum.android.diploma.favourites.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

interface FavouriteVacancyInteractor {
    fun getFavouriteVacancies(): Flow<List<Vacancy>?>

    suspend fun deleteVacancy(vacancyId: String): Boolean

    suspend fun addVacancy(vacancyDetail: VacancyDetail): Boolean

    suspend fun isVacancyFavorite(vacancyId: String): Boolean

    suspend fun getVacancyById(vacancyId: String): VacancyDetail?
}
