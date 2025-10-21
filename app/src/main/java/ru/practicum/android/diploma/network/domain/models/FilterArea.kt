package ru.practicum.android.diploma.network.domain.models

data class FilterArea(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterArea>
)
