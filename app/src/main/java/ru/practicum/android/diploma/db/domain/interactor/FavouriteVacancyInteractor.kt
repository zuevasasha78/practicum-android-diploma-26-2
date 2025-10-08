package ru.practicum.android.diploma.db.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

interface FavouriteVacancyInteractor {
    fun getFavouriteVacancies(): Flow<List<VacancyDetail>?>

    suspend fun deleteVacancy(vacancyId: String): Boolean

    suspend fun addVacancy(vacancyDetail: VacancyDetail): Boolean

    suspend fun isVacancyFavorite(vacancyId: String): Boolean

    suspend fun getVacancyById(vacancyId: String): VacancyDetail?
}
