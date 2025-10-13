package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToFilterIndustry
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToFilterIndustryDto
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class SharedPrefInteractorImpl(
    private val sharedPreferences: SharedPreferencesRepository
) : SharedPrefInteractor {
    override fun getChosenIndustry(): FilterIndustry {
        return sharedPreferences.getChosenIndustry().convertToFilterIndustry()
    }

    override fun setIndustry(industry: FilterIndustry?) {
        sharedPreferences.setIndustry(industry.convertToFilterIndustryDto())
    }

    override fun resetIndustry() {
        sharedPreferences.resetIndustry()
    }
}
