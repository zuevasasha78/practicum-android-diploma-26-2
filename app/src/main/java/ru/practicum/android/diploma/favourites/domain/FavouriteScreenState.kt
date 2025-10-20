package ru.practicum.android.diploma.favourites.domain

import ru.practicum.android.diploma.network.domain.models.VacancyDetail

sealed interface FavouriteScreenState {
    data object Loading : FavouriteScreenState
    data class Content(val vacanciesList: List<VacancyDetail>) : FavouriteScreenState
    data object Empty : FavouriteScreenState
    data object Error : FavouriteScreenState
}
