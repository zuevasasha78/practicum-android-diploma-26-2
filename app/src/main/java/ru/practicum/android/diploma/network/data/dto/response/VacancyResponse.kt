package ru.practicum.android.diploma.network.data.dto.response

data class VacancyResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyDetail>,
)
