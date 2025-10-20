package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class SharedPrefInteractorImpl(
    private val sharedPreferences: SharedPreferencesRepository
) : SharedPrefInteractor {
    override fun getChosenIndustry(): FilterIndustry {
        return sharedPreferences.getChosenIndustry()
    }

    override fun setIndustry(industry: FilterIndustry?) {
        sharedPreferences.setIndustry(industry)
    }

    override fun resetIndustry() {
        sharedPreferences.resetIndustry()
    }

    override fun getSalary(): String {
        return sharedPreferences.getSalary()
    }

    override fun setSalary(salary: String) {
        sharedPreferences.setSalary(salary)
    }

    override fun getOnlyWithSalary(): Boolean {
        return sharedPreferences.getOnlyWithSalary()
    }

    override fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        sharedPreferences.setOnlyWithSalary(onlyWithSalary)
    }

    override fun resetSalarySettings() {
        sharedPreferences.resetSalarySettings()
    }
}
