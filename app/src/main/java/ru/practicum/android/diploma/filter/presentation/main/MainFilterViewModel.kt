package ru.practicum.android.diploma.filter.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractor
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class MainFilterViewModel(private val sharedPrefInteractor: SharedPrefInteractor) : ViewModel() {
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
        _filters.value = _filters.value?.copy(salary = salary) ?: FilterUiState(salary = salary)
    }

    fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        _filters.value =
            _filters.value?.copy(onlyWithSalary = onlyWithSalary) ?: FilterUiState(onlyWithSalary = onlyWithSalary)
    }

    fun getAllFilters() {
        val industry = sharedPrefInteractor.getChosenIndustry()
        if (industry.id != -1) {
            setIndustry(industry)
        }
    }

    fun apply() {
        sharedPrefInteractor.setIndustry(_filters.value?.industry)
    }

    fun reset() {
        _filters.value = FilterUiState()
        sharedPrefInteractor.resetIndustry()
    }
}
