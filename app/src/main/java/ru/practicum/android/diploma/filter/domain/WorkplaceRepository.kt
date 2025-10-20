package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.filter.domain.model.Workplace

interface WorkplaceRepository {

    fun clearWorkplace()
    fun saveWorkplace(workplace: Workplace)
    fun getRegion(): Location?
    fun getCountry(): Location?
}
