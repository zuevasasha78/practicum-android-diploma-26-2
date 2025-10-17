package ru.practicum.android.diploma.filter.presentation.workplace.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.AreaInteractor
import ru.practicum.android.diploma.filter.presentation.workplace.AreaScreenState

class SelectCountryViewModel(private val areaInteractor: AreaInteractor) : ViewModel() {

    private val _countries: MutableLiveData<AreaScreenState> = MutableLiveData<AreaScreenState>()

    val countries: LiveData<AreaScreenState> = _countries

    fun uploadCountry() {
        viewModelScope.launch {
            _countries.postValue(areaInteractor.getCountries())
        }
    }
}
