package ru.practicum.android.diploma.filter.presentation.main

import ru.practicum.android.diploma.network.domain.models.FilterIndustry

data class FilterUiState(
    val placeId: Int? = null,
    val country: String? = null,
    val region: String? = null,
    val industry: FilterIndustry? = null,
    val salary: String = "",
    val onlyWithSalary: Boolean = false,
) {
    val hasAnyFilter: Boolean
        get() = placeId != null || industry != null || salary.isNotBlank() || onlyWithSalary
}
