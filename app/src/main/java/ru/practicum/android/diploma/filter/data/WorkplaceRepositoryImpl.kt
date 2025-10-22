package ru.practicum.android.diploma.filter.data

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.domain.WorkplaceRepository
import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.filter.domain.model.Workplace

class WorkplaceRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : WorkplaceRepository {

    override fun clearWorkplace() {
        setValue(COUNTRY, null)
        setValue(REGION, null)
    }

    override fun saveWorkplace(workplace: Workplace) {
        if (workplace.region != null) {
            setValue(REGION, Location(id = workplace.region!!.id, name = workplace.region!!.name))
            setValue(COUNTRY, workplace.region.parent)
        } else {
            setValue(COUNTRY, workplace.country)
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

    private companion object {
        const val COUNTRY = "country"
        const val REGION = "region"
    }
}
