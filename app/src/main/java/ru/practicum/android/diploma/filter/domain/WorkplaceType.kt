package ru.practicum.android.diploma.filter.domain

data class Workplace(
    val id: Int? = null,
    val value: String?,
    val type: WorkplaceType,
    val isMainSelector: Boolean = false
)

enum class WorkplaceType {
    COUNTRY,
    REGION,
}
