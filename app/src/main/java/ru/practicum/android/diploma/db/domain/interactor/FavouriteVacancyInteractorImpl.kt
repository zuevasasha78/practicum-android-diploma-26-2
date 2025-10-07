package ru.practicum.android.diploma.db.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.domain.VacancyDbRepository
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

class FavouriteVacancyInteractorImpl(private val repository: VacancyDbRepository) : FavouriteVacancyInteractor {
    override fun getFavouriteVacancies(): Flow<List<VacancyDetail>?> {
        return repository.getVacancies()
    }

    override suspend fun deleteVacancy(vacancyId: String): Boolean {
        return repository.deleteVacancy(vacancyId)
    }

    override suspend fun addVacancy(vacancyDetail: VacancyDetail): Boolean {
        return repository.addVacancy(vacancyDetail)
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return repository.isVacancyFavorite(vacancyId)
    }
}
