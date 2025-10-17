package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.presentation.workplace.AreaScreenState

interface AreaInteractor {

    suspend fun getCountries(): AreaScreenState

    suspend fun getRegions(name: String?): AreaScreenState

    suspend fun getRegionsByName(name: String): AreaScreenState
}
