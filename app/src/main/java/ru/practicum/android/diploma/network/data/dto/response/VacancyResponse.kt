package ru.practicum.android.diploma.network.data.dto.response

data class VacancyResponse(
    val found: Integer,
    val pages: Integer,
    val page: Integer,
    val items: List<VacancyDetail>,
)
