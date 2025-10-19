package ru.practicum.android.diploma.filter.presentation.chooser.industries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.IndustriesInteractor
import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class IndustriesChooserViewModel(
    private val industriesInteractor: IndustriesInteractor
) : ViewModel() {
    private val _screenState: MutableLiveData<IndustriesChooserScreenState> = MutableLiveData(
        IndustriesChooserScreenState.Loading
    )
    val screenState: LiveData<IndustriesChooserScreenState> get() = _screenState

    private var allIndustries: List<FilterIndustry> = emptyList()
    private var selectedIndustry: FilterIndustry? = null

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val savedIndustry = industriesInteractor.getSelectedIndustry()
            selectedIndustry = savedIndustry.takeIf { it.id != -1 }
            _screenState.value = industriesInteractor.getIndustries()
            val currentState = _screenState.value
            if (currentState is IndustriesChooserScreenState.Success) {
                allIndustries = currentState.industries
            }
        }
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            _screenState.value = industriesInteractor.getIndustries()
            val currentState = _screenState.value
            if (currentState is IndustriesChooserScreenState.Success) {
                allIndustries = currentState.industries
            }
        }
    }

    fun selectIndustry(industry: FilterIndustry) {
        selectedIndustry = industry
        updateScreenStateWithSelection(true)
    }

    fun clearSelection() {
        selectedIndustry = null
        updateScreenStateWithSelection(false)
    }

    fun filterIndustries(query: String) {
        val filteredList = if (query.isBlank()) {
            allIndustries
        } else {
            allIndustries.filter { industry ->
                industry.name.contains(query, ignoreCase = true)
            }
        }

        val isSelectedIndustryInFilteredList = selectedIndustry?.let { selected ->
            filteredList.any { it.id == selected.id }
        } ?: false

        _screenState.value = if (filteredList.isEmpty()) {
            IndustriesChooserScreenState.Empty
        } else {
            IndustriesChooserScreenState.Success(
                industries = filteredList,
                isChosen = isSelectedIndustryInFilteredList
            )
        }
    }

    private fun updateScreenStateWithSelection(isChosen: Boolean) {
        val currentState = _screenState.value
        if (currentState is IndustriesChooserScreenState.Success) {
            _screenState.value = currentState.copy(isChosen = isChosen)
        }
    }

    fun saveSelectedIndustry() {
        viewModelScope.launch {
            selectedIndustry?.let { industry ->
                industriesInteractor.saveSelectedIndustry(industry)
            } ?: run {
                industriesInteractor.clearSelectedIndustry()
            }
        }
    }

    fun getSelectedIndustry(): FilterIndustry? {
        return selectedIndustry
    }
}
