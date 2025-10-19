package ru.practicum.android.diploma.filter.domain.workplace

import ru.practicum.android.diploma.filter.domain.SharedPreferencesRepository
import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.filter.domain.model.Workplace

class WorkplaceInteractorImpl(private val sharedPrefer: SharedPreferencesRepository) : WorkplaceInteractor {

    override suspend fun getWorkplace(): Workplace {
        val country = sharedPrefer.getValue(COUNTRY, Location::class.java)
        val region = sharedPrefer.getValue(REGION, Location::class.java)
        return Workplace(country, region)
    }

    override suspend fun saveWorkplace(workplace: Workplace) {
        sharedPrefer.setValue(COUNTRY, workplace.country)
        if (workplace.region != null) {
            sharedPrefer.setValue(REGION, Location(id = workplace.region!!.id, name = workplace.region!!.name))
        } else {
            sharedPrefer.setValue(REGION, null)
        }
    }

    override suspend fun clearWorkplace() {
        sharedPrefer.setValue(COUNTRY, null)
        sharedPrefer.setValue(REGION, null)
    }

    override suspend fun getPlaceId(): Int? {
        val region = sharedPrefer.getValue(REGION, Location::class.java)
        if (region != null) {
            return region.id
        }
        val country = sharedPrefer.getValue(COUNTRY, Location::class.java)
        return if (country != null) {
            country.id
        } else {
            null
        }
    }

    companion object {
        private const val COUNTRY = "country"
        private const val REGION = "region"
    }
}
