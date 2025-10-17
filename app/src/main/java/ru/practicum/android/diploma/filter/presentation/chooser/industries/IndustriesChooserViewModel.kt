package ru.practicum.android.diploma.filter.presentation.chooser.industries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.IndustriesInteractor
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractor
import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.network.domain.models.FilterIndustry

class IndustriesChooserViewModel(
    private val industriesInteractor: IndustriesInteractor,
    private val sharedPrefInteractor: SharedPrefInteractor
) : ViewModel() {

    private val _screenState: MutableLiveData<IndustriesChooserScreenState> = MutableLiveData(
        IndustriesChooserScreenState.Loading
    )
    val screenState: LiveData<IndustriesChooserScreenState> get() = _screenState

    private val _selectedIndustry = MutableLiveData<FilterIndustry?>()
    val selectedIndustry: LiveData<FilterIndustry?> get() = _selectedIndustry

    private var allIndustries: List<FilterIndustry> = emptyList()

    init {
        loadIndustries()
        loadSelectedIndustry()
    }

    private fun loadIndustries() {
        viewModelScope.launch {
            _screenState.value = IndustriesChooserScreenState.Loading
            val result = industriesInteractor.getIndustries()

            when (result) {
                is IndustriesChooserScreenState.Success -> {
                    allIndustries = result.industries
                    _screenState.value = if (allIndustries.isEmpty()) {
                        IndustriesChooserScreenState.Empty
                    } else {
                        IndustriesChooserScreenState.Success(
                            industries = allIndustries,
                            isChosen = _selectedIndustry.value != null
                        )
                    }
                }
                is IndustriesChooserScreenState.Error -> {
                    _screenState.value = result
                }
                else -> {}
            }
        }
    }

    private fun loadSelectedIndustry() {
        val chosenIndustry = sharedPrefInteractor.getChosenIndustry()
        if (chosenIndustry.id != -1) {
            _selectedIndustry.value = chosenIndustry
        }
    }

    fun getIndustry(): FilterIndustry {
        return sharedPrefInteractor.getChosenIndustry()
    }

    fun setIndustryId(industry: FilterIndustry) {
        sharedPrefInteractor.setIndustry(industry)
    }

    fun selectIndustry(industry: FilterIndustry) {
        _selectedIndustry.value = industry
        updateScreenStateWithSelection(true)
    }

    fun clearSelection() {
        _selectedIndustry.value = null
        updateScreenStateWithSelection(false)
    }

    fun isIndustrySelected(industry: FilterIndustry): Boolean {
        return _selectedIndustry.value?.id == industry.id
    }

    fun filterIndustries(query: String) {
        val filteredList = if (query.isBlank()) {
            allIndustries
        } else {
            allIndustries.filter { industry ->
                industry.name.contains(query, ignoreCase = true)
            }
        }

        _screenState.value = if (filteredList.isEmpty()) {
            IndustriesChooserScreenState.Empty
        } else {
            IndustriesChooserScreenState.Success(
                industries = filteredList,
                isChosen = _selectedIndustry.value != null
            )
        }
    }

    private fun updateScreenStateWithSelection(isChosen: Boolean) {
        val currentState = _screenState.value
        if (currentState is IndustriesChooserScreenState.Success) {
            _screenState.value = currentState.copy(isChosen = isChosen)
        }
    }

    fun setButtonVisible(isVisible: Boolean) {
        val currentState = _screenState.value
        if (currentState is IndustriesChooserScreenState.Success) {
            _screenState.value = currentState.copy(isChosen = isVisible)
        }
    }
}
