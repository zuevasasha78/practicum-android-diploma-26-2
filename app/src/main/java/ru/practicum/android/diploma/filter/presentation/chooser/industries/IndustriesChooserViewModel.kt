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

    init {
        viewModelScope.launch {
            _screenState.postValue(industriesInteractor.getIndustries())
        }
    }

    fun getIndustry(): FilterIndustry {
        return sharedPrefInteractor.getChosenIndustryId()
    }

    fun setIndustryId(industry: FilterIndustry) {
        sharedPrefInteractor.setIndustryId(industry)
    }

    fun setButtonVisible() {
        when (_screenState.value) {
            is IndustriesChooserScreenState.Success -> {
                _screenState.value = (_screenState.value as IndustriesChooserScreenState.Success).copy(isChosen = true)
            }

            else -> {}
        }
    }
}
