package ru.practicum.android.diploma.filter.domain

interface WorkPlaceInteractor {

    suspend fun getWorkPlace(): List<WorkPlace>
}
