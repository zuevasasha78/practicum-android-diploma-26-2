package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState

interface IndustriesInteractor {

    suspend fun getIndustries(): IndustriesChooserScreenState
}
