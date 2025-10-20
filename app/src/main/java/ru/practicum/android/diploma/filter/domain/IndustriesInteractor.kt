package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

interface IndustriesInteractor {

    suspend fun getIndustries(): IndustriesChooserScreenState
    suspend fun getSelectedIndustry(): FilterIndustry
    suspend fun saveSelectedIndustry(industry: FilterIndustry)
    suspend fun clearSelectedIndustry()
}
