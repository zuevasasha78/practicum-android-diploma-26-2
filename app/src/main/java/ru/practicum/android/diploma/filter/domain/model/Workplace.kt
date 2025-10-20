package ru.practicum.android.diploma.filter.domain.model

data class Workplace(
    val country: Location?,
    val region: Location?
)

data class Location(
    val id: Int,
    val name: String,
    val parent: Location? = null
)
