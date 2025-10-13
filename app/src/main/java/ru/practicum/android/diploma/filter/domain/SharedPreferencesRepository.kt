package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.network.data.dto.response.FilterIndustryDto

interface SharedPreferencesRepository {

    fun getChosenIndustry(): FilterIndustryDto

    fun setIndustry(industry: FilterIndustryDto?)

    fun resetIndustry()

    fun setValue(key: String, value: String?)

    fun getValue(key: String): String?
}
