package ru.practicum.android.diploma.search.data.dto.response

data class VacancyResponseDto(
    val found: Int,
    val pages: Int,
    val page: Int,
    val items: List<VacancyDetailDto>,
)
