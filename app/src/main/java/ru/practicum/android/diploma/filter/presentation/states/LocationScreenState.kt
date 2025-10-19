package ru.practicum.android.diploma.filter.presentation.states

import ru.practicum.android.diploma.filter.presentation.workplace.models.LocationUi

sealed interface LocationScreenState {
    data object Loading : LocationScreenState
    data class Content(val locationUis: List<LocationUi>) : LocationScreenState
    data object Empty : LocationScreenState
    data object Error : LocationScreenState
}
