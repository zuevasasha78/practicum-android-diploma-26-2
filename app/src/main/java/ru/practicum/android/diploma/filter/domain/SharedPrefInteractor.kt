package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.search.domain.model.FilterIndustry

interface SharedPrefInteractor {

    fun getChosenIndustry(): FilterIndustry

    fun setIndustry(industry: FilterIndustry?)

    fun resetIndustry()

    fun getSalary(): String

    fun setSalary(salary: String)

    fun getOnlyWithSalary(): Boolean

    fun setOnlyWithSalary(onlyWithSalary: Boolean)

    fun resetSalarySettings()

    fun isFilterSet(): Boolean
}
