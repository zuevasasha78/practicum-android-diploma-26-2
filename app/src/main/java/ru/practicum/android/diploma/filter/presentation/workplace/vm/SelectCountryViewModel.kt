package ru.practicum.android.diploma.filter.presentation.workplace.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.model.AreaResult
import ru.practicum.android.diploma.filter.domain.workplace.LocationInteractor
import ru.practicum.android.diploma.filter.presentation.states.LocationScreenState
import ru.practicum.android.diploma.filter.presentation.workplace.models.LocationUi
import ru.practicum.android.diploma.utils.DebounceUtils.searchDebounce

class SelectCountryViewModel(private val locationInteractor: LocationInteractor) : ViewModel() {

    private val _countries: MutableLiveData<LocationScreenState> = MutableLiveData<LocationScreenState>()

    val countries: LiveData<LocationScreenState> = _countries

    fun uploadCountry() {
        searchDebounce(viewModelScope) {
            _countries.postValue(LocationScreenState.Loading)
            viewModelScope.launch {
                val areaResult = locationInteractor.getCountries()
                val countriesScreenState: LocationScreenState = when (areaResult) {
                    is AreaResult.Empty -> LocationScreenState.Empty
                    is AreaResult.Error -> LocationScreenState.Error
                    is AreaResult.NoInternetConnection -> LocationScreenState.NoInternet
                    is AreaResult.Success -> {
                        val locationUis = areaResult.areas.map {
                            LocationUi(it.id, it.name)
                        }
                        LocationScreenState.Content(
                            locationUis
                        )
                    }
                }
                _countries.postValue(countriesScreenState)
            }
        }
    }
}
