package ru.practicum.android.diploma.filter.domain

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class SharedPrefInteractorImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : SharedPrefInteractor {
    override fun getChosenIndustry(): FilterIndustry {
        val res = gson.fromJson(
            sharedPreferences.getString(INDUSTRY_TAG, gson.toJson(DEFAULT_INDUSTRY_JSON)),
            FilterIndustry::class.java
        )
        return res
    }

    override fun setIndustry(industry: FilterIndustry?) {
        sharedPreferences.edit().putString(INDUSTRY_TAG, gson.toJson(industry)).apply()
    }

    override fun resetIndustry() {
        sharedPreferences.edit().putString(INDUSTRY_TAG, gson.toJson(DEFAULT_INDUSTRY_JSON)).apply()
    }

    companion object {
        const val INDUSTRY_TAG = "INDUSTRY_TAG"
        private val DEFAULT_INDUSTRY_JSON = FilterIndustry(-1, "")
    }
}
