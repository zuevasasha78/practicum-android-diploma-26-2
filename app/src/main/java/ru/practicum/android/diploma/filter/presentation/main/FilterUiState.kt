package ru.practicum.android.diploma.filter.presentation.main

data class FilterUiState(
    val place: String = "",
    val industry: String = "",
    val salary: String = "",
    val onlyWithSalary: Boolean = false,
) {
    val hasAnyFilter: Boolean
        get() = place.isNotBlank() || industry.isNotBlank() || salary.isNotBlank() || onlyWithSalary
}
