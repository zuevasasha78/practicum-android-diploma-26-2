package ru.practicum.android.diploma.filter.presentation.industries_chooser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.IndustriesInteractor
import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState

class IndustriesChooserViewModel(industriesInteractor: IndustriesInteractor): ViewModel() {

    private val _screenState: MutableLiveData<IndustriesChooserScreenState> = MutableLiveData()
    val screenState: LiveData<IndustriesChooserScreenState> get() = _screenState

    init {
        viewModelScope.launch {
            _screenState.postValue(industriesInteractor.getIndustries())
        }
    }
}
