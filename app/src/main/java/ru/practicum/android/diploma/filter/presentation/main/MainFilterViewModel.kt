package ru.practicum.android.diploma.filter.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractor
import ru.practicum.android.diploma.filter.domain.workplace.WorkplaceInteractor
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceConvertor.convertToWorkplaceUi
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceUi
import ru.practicum.android.diploma.search.domain.model.FilterIndustry

class MainFilterViewModel(
    private val sharedPrefInteractor: SharedPrefInteractor,
    private val workplaceInteractor: WorkplaceInteractor
) : ViewModel() {
    private val _filters = MutableLiveData(FilterUiState())
    val filters: LiveData<FilterUiState> = _filters

    init {
        getAllFilters()
    }

    fun clearPlace() {
        _filters.value = _filters.value?.copy(
            placeId = null,
            country = null,
            region = null,
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
        viewModelScope.launch {
            val workplace = workplaceInteractor.getWorkplace().convertToWorkplaceUi()
            val placeId = getPlaceId(workplace)
            _filters.value = FilterUiState(
                placeId = placeId,
                country = workplace.country?.name,
                region = workplace.region?.name,
                industry = sharedPrefInteractor.getChosenIndustry().takeIf { it.id != -1 },
                salary = sharedPrefInteractor.getSalary(),
                onlyWithSalary = sharedPrefInteractor.getOnlyWithSalary()
            )
        }
    }

    fun apply() {
        sharedPrefInteractor.setIndustry(_filters.value?.industry)
    }

    fun reset() {
        _filters.value = FilterUiState()
        viewModelScope.launch {
            sharedPrefInteractor.resetIndustry()
            sharedPrefInteractor.resetSalarySettings()
            workplaceInteractor.clearWorkplace()
        }
    }

    private fun getPlaceId(workplace: WorkplaceUi): Int? {
        return if (workplace.region == null) {
            workplace.region?.id
        } else {
            workplace.country?.id
        }
    }
}
