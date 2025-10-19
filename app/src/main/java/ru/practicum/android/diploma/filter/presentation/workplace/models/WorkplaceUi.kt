package ru.practicum.android.diploma.filter.presentation.workplace.models

data class WorkplaceUi(
    val country: LocationUi?,
    val region: LocationUi?
)

data class LocationUi(
    val id: Int,
    val name: String,
    val parent: LocationUi? = null,
)
