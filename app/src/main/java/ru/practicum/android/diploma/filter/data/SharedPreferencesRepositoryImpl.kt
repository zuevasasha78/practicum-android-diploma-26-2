package ru.practicum.android.diploma.filter.data

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.domain.SharedPreferencesRepository
import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.filter.domain.model.Workplace
import ru.practicum.android.diploma.network.data.dto.response.FilterIndustryDto

class SharedPreferencesRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : SharedPreferencesRepository {

    override fun getChosenIndustry(): FilterIndustryDto {
        val res = gson.fromJson(
            sharedPreferences.getString(INDUSTRY_TAG, gson.toJson(DEFAULT_INDUSTRY_JSON)),
            FilterIndustryDto::class.java
        )
        return res
    }

    override fun setIndustry(industry: FilterIndustryDto?) {
        val industryToSave = industry ?: DEFAULT_INDUSTRY_JSON
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

    override fun clearWorkplace() {
        setValue(COUNTRY, null)
        setValue(REGION, null)
    }

    override fun saveWorkplace(workplace: Workplace) {
        setValue(COUNTRY, workplace.country)
        if (workplace.region != null) {
            setValue(REGION, Location(id = workplace.region!!.id, name = workplace.region!!.name))
        } else {
            setValue(REGION, null)
        }
    }

    override fun getRegion(): Location? {
       return getValue(REGION, Location::class.java)
    }

    override fun getCountry(): Location? {
       return getValue(COUNTRY, Location::class.java)
    }

    private fun <T> setValue(key: String, value: T?) {
        val json = gson.toJson(value)
        return sharedPreferences.edit().putString(key, json).apply()
    }

    private fun <T> getValue(key: String, clazz: Class<T>): T? {
        val json = sharedPreferences.getString(key, null)
        return json?.let { gson.fromJson(it, clazz) }
    }

    companion object {
        const val INDUSTRY_TAG = "INDUSTRY_TAG"
        const val SALARY_TAG = "SALARY_TAG"
        const val ONLY_WITH_SALARY_TAG = "ONLY_WITH_SALARY_TAG"
        private val DEFAULT_INDUSTRY_JSON = FilterIndustryDto(-1, "")

        private const val COUNTRY = "country"
        private const val REGION = "region"
    }
}
