package ru.practicum.android.diploma.network.data.dto.requests

data class VacanciesFilter(
    val area: Int? = null,
    val industry: Int? = null,
    val text: Int? = null,
    val salary: Int? = null,
    val page: Int? = null,
    val onlyWithSalary: Boolean? = null,
)
