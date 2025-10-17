package ru.practicum.android.diploma.filter.presentation.workplace.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.AreaInteractor
import ru.practicum.android.diploma.filter.presentation.workplace.AreaScreenState
import ru.practicum.android.diploma.utils.DebounceUtils.searchDebounce

class SelectRegionViewModel(private val areaInteractor: AreaInteractor) : ViewModel() {

    private val _regions: MutableLiveData<AreaScreenState> = MutableLiveData<AreaScreenState>()

    val regions: LiveData<AreaScreenState> = _regions
    private var lastSearch = ""

    fun uploadCountry(name: String?) {
        viewModelScope.launch {
            _regions.postValue(areaInteractor.getRegions(name))
        }
    }

    fun searchRegionDebounce(text: String) {
        if (lastSearch == text) return
        _regions.postValue(AreaScreenState.Loading)
        lastSearch = text
        searchDebounce(viewModelScope) {
            _regions.postValue(areaInteractor.getRegionsByName(text))
        }
    }
}
