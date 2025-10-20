package ru.practicum.android.diploma.filter.domain.workplace

import ru.practicum.android.diploma.filter.domain.SharedPreferencesRepository
import ru.practicum.android.diploma.filter.domain.model.Workplace

class WorkplaceInteractorImpl(private val sharedPrefer: SharedPreferencesRepository) : WorkplaceInteractor {

    override suspend fun getWorkplace(): Workplace {
        val country = sharedPrefer.getCountry()
        val region = sharedPrefer.getRegion()
        return Workplace(country, region)
    }

    override suspend fun saveWorkplace(workplace: Workplace) {
        sharedPrefer.saveWorkplace(workplace)
    }

    override suspend fun clearWorkplace() {
        sharedPrefer.clearWorkplace()
    }

    override suspend fun getPlaceId(): Int? {
        val region = sharedPrefer.getRegion()
        if (region != null) {
            return region.id
        }
        val country = sharedPrefer.getCountry()
        return if (country != null) {
            country.id
        } else {
            null
        }
    }
}
