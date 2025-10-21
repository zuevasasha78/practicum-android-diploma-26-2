package ru.practicum.android.diploma.favourites.presentation

import ru.practicum.android.diploma.network.domain.models.Vacancy

sealed interface FavouriteScreenState {
    data object Loading : FavouriteScreenState
    data class Content(val vacanciesList: List<Vacancy>) : FavouriteScreenState
    data object Empty : FavouriteScreenState
    data object Error : FavouriteScreenState
}
