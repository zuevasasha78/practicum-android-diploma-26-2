package ru.practicum.android.diploma.vacancy.domain

import ru.practicum.android.diploma.vacancy.domain.model.VacancyModel


sealed class VacancyState {
    object Loading : VacancyState()
    data class Content(val vacancy: VacancyModel) : VacancyState()
    object NoInternet : VacancyState()
    object ServerError : VacancyState()
    object VacancyNotFound : VacancyState()
}
