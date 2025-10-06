package ru.practicum.android.diploma.network.domain.models

data class VacancyResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<Vacancy>,
)
