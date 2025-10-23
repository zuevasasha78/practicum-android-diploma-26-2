package ru.practicum.android.diploma.filter.domain.model

import ru.practicum.android.diploma.search.domain.model.FilterIndustry

sealed interface IndustriesChooserScreenState {
    data object Loading : IndustriesChooserScreenState
    data class Success(
        val industries: List<FilterIndustry>,
        val isChosen: Boolean = false
    ) : IndustriesChooserScreenState
    data object NoResult : IndustriesChooserScreenState
    data object ServerError : IndustriesChooserScreenState
    data object NoInternet : IndustriesChooserScreenState
}
