package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.network.domain.models.FilterIndustry

sealed class IndustriesChooserScreenState {
    data object Loading : IndustriesChooserScreenState()
    data class Success(
        val industries: List<FilterIndustry>,
        val isChosen: Boolean = false
    ) : IndustriesChooserScreenState()
    data class Error(val placeholder: IndustriesPlaceholder) : IndustriesChooserScreenState()
}
