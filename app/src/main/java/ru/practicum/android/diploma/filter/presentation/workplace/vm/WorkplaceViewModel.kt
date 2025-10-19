package ru.practicum.android.diploma.filter.presentation.workplace.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.workplace.WorkplaceInteractor
import ru.practicum.android.diploma.filter.presentation.workplace.models.LocationUi
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceConvertor.convertToWorkplace
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceConvertor.convertToWorkplaceUi
import ru.practicum.android.diploma.filter.presentation.workplace.models.WorkplaceUi

class WorkplaceViewModel(private val workplaceInteractor: WorkplaceInteractor) : ViewModel() {

    val workplaceUiMutable: MutableLiveData<WorkplaceUi> = MutableLiveData(WorkplaceUi(null, null))
    val workplaceUi: LiveData<WorkplaceUi> = workplaceUiMutable

    init {
        viewModelScope.launch {
            val result = workplaceInteractor.getWorkplace().convertToWorkplaceUi()
            workplaceUiMutable.postValue(result)
        }
    }

    fun uploadWorkplace(country: LocationUi?, region: LocationUi?) {
        viewModelScope.launch {
            val current = workplaceUi.value
            val updated = if (country != null || region != null) {
                val oldCountry = current.region?.parent?.id
                val newCountryFromRegion = region?.parent?.id
                if (oldCountry != newCountryFromRegion && current.region != null) {
                    current.copy(country = country, region = null)
                } else {
                    current.copy(
                        country = country ?: current.country,
                        region = region ?: current.region
                    )
                }
            } else {
                workplaceInteractor.getWorkplace().convertToWorkplaceUi()
            }
            workplaceUiMutable.postValue(updated)
        }
    }

    fun clearCountry() {
        workplaceUiMutable.postValue(workplaceUi.value.copy(country = null))
    }

    fun clearRegion() {
        workplaceUiMutable.postValue(workplaceUi.value.copy(region = null))
    }

    fun saveWorkplace(workplaces: WorkplaceUi) {
        viewModelScope.launch {
            workplaceInteractor.saveWorkplace(workplaces.convertToWorkplace())
        }
    }
}
