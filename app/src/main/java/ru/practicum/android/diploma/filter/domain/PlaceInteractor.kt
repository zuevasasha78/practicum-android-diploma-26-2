package ru.practicum.android.diploma.filter.domain

interface PlaceInteractor {

    fun getCountry(): String?
    fun getRegion(): String?
    fun resetPlace()
}
