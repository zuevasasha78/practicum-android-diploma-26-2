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

    fun uploadWorkplace(countryValue: String?, regionValue: String?) {
        viewModelScope.launch {
            _workplace.postValue(workplaceInteractor.getWorkplace(countryValue, regionValue))
        }
    }

    fun clearCountry() {
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
        val workplace = _workplace.value.map { place ->
            if (place.type == WorkplaceType.REGION) {
                place.copy(value = null)
            } else {
                place
            }
        }
        _workplace.postValue(workplace)
    }

    fun updateWorkplace(country: String?, region: String?, placeId: String?) {
        val workplace = _workplace.value.map { place ->
            when (place.type) {
                WorkplaceType.COUNTRY -> {
                    place.copy(value = country)
                }
                WorkplaceType.REGION -> {
                    place.copy(value = region)
                }
            }
        }
        _workplace.postValue(workplace)
        viewModelScope.launch {
            workplaceInteractor.updateWorkplace(country, region, placeId)
        }
    }
}
