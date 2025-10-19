package ru.practicum.android.diploma.filter.domain

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

    fun <T> setValue(key: String, value: T?)
    fun <T> getValue(key: String, clazz: Class<T>): T?
}
