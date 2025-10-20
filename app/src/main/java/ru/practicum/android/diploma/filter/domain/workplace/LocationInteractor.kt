package ru.practicum.android.diploma.filter.domain.workplace

import ru.practicum.android.diploma.filter.domain.model.AreaResult

interface LocationInteractor {

    suspend fun getCountries(): AreaResult

    suspend fun getRegionsById(id: Int?): AreaResult

    suspend fun getRegionsByName(name: String, id: Int?): AreaResult
}
