package ru.practicum.android.diploma.filter.presentation.workplace.models

import ru.practicum.android.diploma.R

sealed class WorkplaceType(val location: LocationUi?, val title: Int) {
    data class Country(val countryLocation: LocationUi?) : WorkplaceType(countryLocation, R.string.country)
    data class Region(val regionLocation: LocationUi?) : WorkplaceType(regionLocation, R.string.region)
}
