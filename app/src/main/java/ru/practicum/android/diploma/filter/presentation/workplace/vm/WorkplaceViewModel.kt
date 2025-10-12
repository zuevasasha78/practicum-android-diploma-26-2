package ru.practicum.android.diploma.filter.presentation.workplace.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.Workplace
import ru.practicum.android.diploma.filter.domain.WorkplaceInteractor
import ru.practicum.android.diploma.filter.domain.WorkplaceType

class WorkplaceViewModel(private val workplaceInteractor: WorkplaceInteractor) : ViewModel() {

    private val _workplace: MutableLiveData<List<Workplace>> = MutableLiveData<List<Workplace>>()

    val workplace: LiveData<List<Workplace>> = _workplace
    var countyValue: String? = null
    var regionValue: String? = null

    fun uploadWorkplace(regionValue: String?, countryValue: String?) {
        this.countyValue = countryValue
        this.regionValue = regionValue
        viewModelScope.launch {
            _workplace.postValue(workplaceInteractor.getWorkplace(regionValue, countryValue))
        }
    }

    fun clearCountry() {
        countyValue = null
        val workplace = _workplace.value.map { place ->
            if (place.type == WorkplaceType.COUNTRY) {
                place.copy(value = null)
            } else {
                place
            }
        }
        _workplace.postValue(workplace)
    }

    fun clearRegion() {
        regionValue = null
        val workplace = _workplace.value.map { place ->
            if (place.type == WorkplaceType.REGION) {
                place.copy(value = null)
            } else {
                place
            }
        }
        _workplace.postValue(workplace)
    }
}
