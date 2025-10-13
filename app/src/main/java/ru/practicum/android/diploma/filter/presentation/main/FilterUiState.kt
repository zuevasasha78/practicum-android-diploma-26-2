package ru.practicum.android.diploma.filter.presentation.main

import ru.practicum.android.diploma.network.domain.models.FilterIndustry

data class FilterUiState(
    val place: String = "",
    val industry: FilterIndustry? = null,
    val salary: String = "",
    val onlyWithSalary: Boolean = false,
) {
    val hasAnyFilter: Boolean
        get() = place.isNotBlank() || industry != null || salary.isNotBlank() || onlyWithSalary
}
