package ru.practicum.android.diploma.filter.presentation.workplace

import ru.practicum.android.diploma.filter.domain.Workplace

sealed interface AreaScreenState {
    data object Loading : AreaScreenState
    data class Content(val areas: List<Workplace>) : AreaScreenState
    data object Empty : AreaScreenState
    data object Error : AreaScreenState
}
