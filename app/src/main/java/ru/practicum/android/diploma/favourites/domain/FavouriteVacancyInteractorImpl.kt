package ru.practicum.android.diploma.favourites.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favourites.domain.db.VacancyDbRepository
import ru.practicum.android.diploma.favourites.domain.db.FavouriteVacancyInteractor
import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

class FavouriteVacancyInteractorImpl(private val repository: VacancyDbRepository) :
    FavouriteVacancyInteractor {
    override fun getFavouriteVacancies(): Flow<List<Vacancy>?> {
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

    override suspend fun getVacancyById(vacancyId: String): VacancyDetail? {
        return repository.getVacancyById(vacancyId)
    }
}
