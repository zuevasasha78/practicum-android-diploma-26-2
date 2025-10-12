package ru.practicum.android.diploma.network.data.dto.response

data class FilterArea(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterArea>
)
