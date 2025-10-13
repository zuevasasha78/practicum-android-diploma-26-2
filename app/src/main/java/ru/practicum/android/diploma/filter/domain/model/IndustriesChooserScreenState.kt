package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.network.domain.models.FilterIndustry
import ru.practicum.android.diploma.search.presentation.models.Placeholder

sealed class IndustriesChooserScreenState {
    data object Loading : IndustriesChooserScreenState()
    data class Success(val industriesList: List<FilterIndustry>, val isChosen: Boolean = false) :
        IndustriesChooserScreenState()

    data class Error(val placeholder: Placeholder) : IndustriesChooserScreenState()
}
