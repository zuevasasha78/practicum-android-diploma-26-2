package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.filter.domain.model.Workplace
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustryDto

interface SharedPreferencesRepository {

    fun getChosenIndustry(): FilterIndustryDto

    fun setIndustry(industry: FilterIndustryDto?)

    fun resetIndustry()

    fun getSalary(): String

    fun setSalary(salary: String)

    fun getOnlyWithSalary(): Boolean

    fun setOnlyWithSalary(onlyWithSalary: Boolean)

    fun resetSalarySettings()

    fun clearWorkplace()
    fun saveWorkplace(workplace: Workplace)
    fun getRegion(): Location?
    fun getCountry(): Location?
}
