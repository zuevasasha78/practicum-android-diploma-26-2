package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.network.domain.models.FilterIndustry
import ru.practicum.android.diploma.search.presentation.models.Placeholder

sealed class IndustriesChooserScreenState {
    data object Loading : IndustriesChooserScreenState()
    data object Empty : IndustriesChooserScreenState()
    data class Success(
        val industries: List<FilterIndustry>,
        val isChosen: Boolean = false
    ) : IndustriesChooserScreenState()
    data class Error(val placeholder: Placeholder) : IndustriesChooserScreenState()
}
