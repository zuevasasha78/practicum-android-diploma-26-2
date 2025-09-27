package ru.practicum.android.diploma.network.data.dto

data class VacancyResponse(
    val found: Integer,
    val pages: Integer,
    val page: Integer,
    val vacancies: List<VacancyDetail>,
)
