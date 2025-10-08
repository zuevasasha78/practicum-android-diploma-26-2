package ru.practicum.android.diploma.favourites.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.network.domain.models.Vacancy

interface FavouriteVacancyInteractor {
    fun getFavouriteVacancies(): Flow<List<Vacancy>?>
}
