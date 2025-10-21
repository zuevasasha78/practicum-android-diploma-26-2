package ru.practicum.android.diploma.search.data.dto.response

data class FilterAreaDto(
    val id: Int,
    val name: String,
    val parentId: Int?,
    val areas: List<FilterAreaDto>
)
