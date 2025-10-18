package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

interface IndustriesInteractor {

    suspend fun getIndustries(): IndustriesChooserScreenState
    fun getSelectedIndustry(): FilterIndustry
    fun saveSelectedIndustry(industry: FilterIndustry)
    fun clearSelectedIndustry()
}
