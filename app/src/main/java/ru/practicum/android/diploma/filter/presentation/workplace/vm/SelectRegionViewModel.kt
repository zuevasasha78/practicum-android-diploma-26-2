package ru.practicum.android.diploma.filter.presentation.workplace.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.model.AreaResult
import ru.practicum.android.diploma.filter.domain.workplace.LocationInteractor
import ru.practicum.android.diploma.filter.presentation.states.LocationScreenState
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceConvertor.convertToLocationUi
import ru.practicum.android.diploma.utils.DebounceUtils.searchDebounce

class SelectRegionViewModel(private val locationInteractor: LocationInteractor) : ViewModel() {

    private val _regions: MutableLiveData<LocationScreenState> = MutableLiveData<LocationScreenState>()

    val regions: LiveData<LocationScreenState> = _regions

    private var lastSearch = ""

    fun uploadRegions(id: Int?) {
        uploadRegions { locationInteractor.getRegionsById(id) }
    }

    fun searchRegions(name: String, id: Int?) {
        if (lastSearch == name) return
        lastSearch = name
        searchDebounce(viewModelScope) {
            uploadRegions {
                locationInteractor.getRegionsByName(name, id)
            }
        }
    }

    private fun uploadRegions(block: suspend () -> AreaResult) {
        _regions.postValue(LocationScreenState.Loading)
        searchDebounce(viewModelScope) {
            viewModelScope.launch {
                val areaResult = block()
                val locationScreenState = when (areaResult) {
                    is AreaResult.Empty -> LocationScreenState.Empty
                    is AreaResult.Error -> LocationScreenState.Error
                    is AreaResult.Success -> {
                        val locationUis = areaResult.areas.map { it.convertToLocationUi() }
                        if (locationUis.isEmpty()) {
                            LocationScreenState.Empty
                        } else {
                            LocationScreenState.Content(
                                locationUis
                            )
                        }
                    }
                }
                _regions.postValue(locationScreenState)
            }
        }
    }
}
