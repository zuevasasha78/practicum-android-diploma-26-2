package ru.practicum.android.diploma.filter.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class MainFilterViewModel : ViewModel() {
    private val _filters = MutableLiveData(FilterUiState())
    val filters: LiveData<FilterUiState> = _filters
    val buttonsVisible: LiveData<Boolean> = _filters.map { it.hasAnyFilter }

    fun onPlaceChanged(v: String) {
        _filters.value = _filters.value!!.copy(place = v)
    }

    fun onIndustryChanged(v: String) {
        _filters.value = _filters.value!!.copy(industry = v)
    }

    fun onSalaryChanged(v: String) {
        _filters.value = _filters.value!!.copy(salary = v)
    }

    fun onOnlyWithSalaryChanged(v: Boolean) {
        _filters.value = _filters.value!!.copy(onlyWithSalary = v)
    }

    fun reset() {
        _filters.value = FilterUiState()
    }
}
