package ru.practicum.android.diploma.filter.domain.model

sealed interface AreaResult {
    data class Success(val areas: List<Location>) : AreaResult
    data object Empty : AreaResult
    data object Error : AreaResult
}
