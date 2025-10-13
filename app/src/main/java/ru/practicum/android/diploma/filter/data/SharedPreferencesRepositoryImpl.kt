package ru.practicum.android.diploma.filter.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.domain.SharedPreferencesRepository
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
        sharedPreferences.edit().putString(INDUSTRY_TAG, gson.toJson(industry)).apply()
    }

    override fun resetIndustry() {
        sharedPreferences.edit().putString(INDUSTRY_TAG, gson.toJson(DEFAULT_INDUSTRY_JSON)).apply()
    }

    override fun setValue(key: String, value: String?) {
        sharedPreferences.edit { putString(key, value) }
    }

    override fun getValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    companion object {
        const val INDUSTRY_TAG = "INDUSTRY_TAG"
        private val DEFAULT_INDUSTRY_JSON = FilterIndustryDto(-1, "")
    }
}
