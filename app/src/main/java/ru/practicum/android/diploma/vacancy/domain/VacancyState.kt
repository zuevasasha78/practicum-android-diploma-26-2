package ru.practicum.android.diploma.vacancy.domain

import ru.practicum.android.diploma.network.domain.models.VacancyDetail

sealed class VacancyState {
    data object Loading : VacancyState()
    data class Content(val vacancy: VacancyDetail) : VacancyState()
    data object NoInternet : VacancyState()
    data object ServerError : VacancyState()
    data object VacancyNotFound : VacancyState()
}
