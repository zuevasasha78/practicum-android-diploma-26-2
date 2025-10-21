package ru.practicum.android.diploma.filter.data

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.domain.SharedPreferencesRepository
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToFilterIndustry
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToFilterIndustryDto
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustryDto
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class SharedPreferencesRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : SharedPreferencesRepository {

    override fun getChosenIndustry(): FilterIndustry {
        val res = gson.fromJson(
            sharedPreferences.getString(INDUSTRY_TAG, gson.toJson(DEFAULT_INDUSTRY_JSON)),
            FilterIndustryDto::class.java
        )
        return res.convertToFilterIndustry()
    }

    override fun setIndustry(industry: FilterIndustry?) {
        val industryToSave = industry.convertToFilterIndustryDto() ?: DEFAULT_INDUSTRY_JSON
        val jsonString = gson.toJson(industryToSave)
        sharedPreferences.edit().putString(INDUSTRY_TAG, jsonString).apply()
    }

    override fun resetIndustry() {
        val jsonString = gson.toJson(DEFAULT_INDUSTRY_JSON)
        sharedPreferences.edit().putString(INDUSTRY_TAG, jsonString).apply()
    }

    override fun getSalary(): String {
        return sharedPreferences.getString(SALARY_TAG, "") ?: ""
    }

    override fun setSalary(salary: String) {
        sharedPreferences.edit().putString(SALARY_TAG, salary).apply()
    }

    override fun getOnlyWithSalary(): Boolean {
        return sharedPreferences.getBoolean(ONLY_WITH_SALARY_TAG, false)
    }

    override fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        sharedPreferences.edit().putBoolean(ONLY_WITH_SALARY_TAG, onlyWithSalary).apply()
    }

    override fun resetSalarySettings() {
        sharedPreferences.edit()
            .remove(SALARY_TAG)
            .remove(ONLY_WITH_SALARY_TAG)
            .apply()
    }

    companion object {
        const val INDUSTRY_TAG = "INDUSTRY_TAG"
        const val SALARY_TAG = "SALARY_TAG"
        const val ONLY_WITH_SALARY_TAG = "ONLY_WITH_SALARY_TAG"
        private val DEFAULT_INDUSTRY_JSON = FilterIndustryDto(-1, "")
    }
}
