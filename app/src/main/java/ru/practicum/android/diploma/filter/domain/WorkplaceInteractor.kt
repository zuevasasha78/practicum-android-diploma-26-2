package ru.practicum.android.diploma.filter.domain

interface WorkplaceInteractor {

    suspend fun getWorkplace(countryValue: String?, regionValue: String?): List<Workplace>
    suspend fun updateWorkplace(country: String?, region: String?)
}
