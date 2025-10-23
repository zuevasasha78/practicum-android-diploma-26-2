package ru.practicum.android.diploma.search.data.dto.requests

data class VacanciesFilterDto(
    val area: Int? = null,
    val industry: Int? = null,
    val text: String? = null,
    val salary: Int? = null,
    val page: Int? = null,
    val onlyWithSalary: Boolean? = null,
)
