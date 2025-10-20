package ru.practicum.android.diploma.filter.domain.workplace

import ru.practicum.android.diploma.filter.domain.model.Workplace

interface WorkplaceInteractor {

    suspend fun getWorkplace(): Workplace
    suspend fun saveWorkplace(workplace: Workplace)

    suspend fun clearWorkplace()
    suspend fun getPlaceId(): Int?
}
