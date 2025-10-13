package ru.practicum.android.diploma.filter.domain

class WorkplaceInteractorImpl(private val sharedPrefer: SharedPreferencesRepository) : WorkplaceInteractor {

    override suspend fun getWorkplace(countryValue: String?, regionValue: String?): List<Workplace> {
        val countryValue = countryValue ?: sharedPrefer.getValue(COUNTRY)
        val regionValue = regionValue ?: sharedPrefer.getValue(REGION)
        return listOf(
            Workplace(value = countryValue, type = WorkplaceType.COUNTRY, isMainSelector = true),
            Workplace(value = regionValue, type = WorkplaceType.REGION, isMainSelector = true),
        )
    }

    override suspend fun updateWorkplace(country: String?, region: String?) {
        sharedPrefer.setValue(COUNTRY, country)
        sharedPrefer.setValue(REGION, region)
    }

    companion object {
        private const val COUNTRY = "country"
        private const val REGION = "region"
    }
}
