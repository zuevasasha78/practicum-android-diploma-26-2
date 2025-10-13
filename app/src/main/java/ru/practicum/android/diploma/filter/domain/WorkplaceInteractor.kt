package ru.practicum.android.diploma.filter.domain

interface WorkplaceInteractor {

    suspend fun getWorkplace(regionValue: String?, countryValue: String?): List<Workplace>
}
