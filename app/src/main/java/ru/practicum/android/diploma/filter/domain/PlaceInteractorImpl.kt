package ru.practicum.android.diploma.filter.domain

class PlaceInteractorImpl(private val sharedPreferences: SharedPreferencesRepository) : PlaceInteractor {

    override fun getCountry(): String? {
        return sharedPreferences.getValue(COUNTRY)
    }

    override fun getRegion(): String? {
        return sharedPreferences.getValue(REGION)
    }

    override fun resetPlace() {
        sharedPreferences.setValue(COUNTRY, null)
        sharedPreferences.setValue(REGION, null)
    }

    companion object {
        private const val COUNTRY = "country"
        private const val REGION = "region"
    }
}
