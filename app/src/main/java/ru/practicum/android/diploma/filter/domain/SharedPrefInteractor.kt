package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.network.domain.models.FilterIndustry

interface SharedPrefInteractor {

    fun getChosenIndustry(): FilterIndustry

    fun setIndustry(industry: FilterIndustry?)

    fun resetIndustry()
}
