package ru.practicum.android.diploma.favourites.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.domain.VacancyDbRepository
import ru.practicum.android.diploma.network.domain.models.Vacancy

class FavouriteVacancyInteractorImpl(private val repository: VacancyDbRepository) : FavouriteVacancyInteractor {
    override fun getFavouriteVacancies(): Flow<List<Vacancy>?> {
        return repository.getVacancies()
    }
}
