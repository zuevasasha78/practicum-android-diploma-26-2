package ru.practicum.android.diploma.filter.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.PlaceInteractor
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractor
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class MainFilterViewModel(
    private val sharedPrefInteractor: SharedPrefInteractor,
    private val placeInteractor: PlaceInteractor
) : ViewModel() {
    private val _filters = MutableLiveData(FilterUiState())
    val filters: LiveData<FilterUiState> = _filters

    init {
        getAllFilters()
    }

    fun setPlace(country: String?, region: String?) {
        _filters.value = _filters.value?.copy(
            country = country,
            region = region
        ) ?: FilterUiState(
            country = country,
            region = region
        )
    }

    fun setIndustry(industry: FilterIndustry) {
        _filters.value = _filters.value?.copy(industry = industry) ?: FilterUiState(industry = industry)
    }

    fun setSalary(salary: String) {
        sharedPrefInteractor.setSalary(salary)
        _filters.value = _filters.value?.copy(salary = salary) ?: FilterUiState(salary = salary)
    }

    fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        sharedPrefInteractor.setOnlyWithSalary(onlyWithSalary)
        _filters.value =
            _filters.value?.copy(onlyWithSalary = onlyWithSalary) ?: FilterUiState(onlyWithSalary = onlyWithSalary)
    }

    fun getAllFilters() {
        _filters.value = FilterUiState(
            placeId = placeInteractor.getPlaceId(),
            country = placeInteractor.getCountry(),
            region = placeInteractor.getRegion(),
            industry = sharedPrefInteractor.getChosenIndustry().takeIf { it.id != -1 },
            salary = sharedPrefInteractor.getSalary(),
            onlyWithSalary = sharedPrefInteractor.getOnlyWithSalary()
        )
    }

    fun apply() {
        sharedPrefInteractor.setIndustry(_filters.value?.industry)
    }

    fun reset() {
        _filters.value = FilterUiState()
        sharedPrefInteractor.resetIndustry()
        sharedPrefInteractor.resetSalarySettings()
        placeInteractor.resetPlace()
    }
}
