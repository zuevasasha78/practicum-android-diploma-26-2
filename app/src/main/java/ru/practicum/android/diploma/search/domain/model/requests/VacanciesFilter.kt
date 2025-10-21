package ru.practicum.android.diploma.search.domain.model.requests

data class VacanciesFilter(
    val area: Int? = null,
    val industry: Int? = null,
    val text: String? = null,
    val salary: Int? = null,
    val page: Int? = null,
    val onlyWithSalary: Boolean? = null,
)
