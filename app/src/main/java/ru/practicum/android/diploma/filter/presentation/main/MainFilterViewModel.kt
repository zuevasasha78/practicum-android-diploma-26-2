package ru.practicum.android.diploma.filter.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainFilterViewModel : ViewModel() {
    private val _filters = MutableLiveData(FilterUiState())
    val filters: LiveData<FilterUiState> = _filters

    fun setPlace(place: String) {
        _filters.value = _filters.value?.copy(place = place) ?: FilterUiState(place = place)
    }

    fun setIndustry(industry: String) {
        _filters.value = _filters.value?.copy(industry = industry) ?: FilterUiState(industry = industry)
    }

    fun setSalary(salary: String) {
        _filters.value = _filters.value?.copy(salary = salary) ?: FilterUiState(salary = salary)
    }

    fun setOnlyWithSalary(onlyWithSalary: Boolean) {
        _filters.value =
            _filters.value?.copy(onlyWithSalary = onlyWithSalary) ?: FilterUiState(onlyWithSalary = onlyWithSalary)
    }

    fun reset() {
        _filters.value = FilterUiState()
    }
}
