package ru.practicum.android.diploma.vacancy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.db.domain.interactor.FavouriteVacancyInteractor
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.vacancy.domain.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.VacancyState

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val favouriteVacancyInteractor: FavouriteVacancyInteractor,
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun loadVacancy(vacancyId: String) {
        _state.value = VacancyState.Loading

        viewModelScope.launch {
            val result = vacancyInteractor.getVacancy(vacancyId)
            _state.value = result
            if (result is VacancyState.Content) {
                val isFavorite = favouriteVacancyInteractor.isVacancyFavorite(vacancyId)
                _isFavorite.value = isFavorite
            }
        }
    }

    suspend fun toggleFavorite(vacancyId: String, vacancy: VacancyDetail? = null) {
        val currentState = _isFavorite.value ?: false

        if (currentState) {
            val success = favouriteVacancyInteractor.deleteVacancy(vacancyId)
            if (success) {
                _isFavorite.value = false
            }
        } else {
            vacancy?.let {
                val success = favouriteVacancyInteractor.addVacancy(it)
                if (success) {
                    _isFavorite.value = true
                }
            }
        }
    }

    fun prepareShareContent(vacancy: VacancyDetail): String {
        return vacancyInteractor.prepareShareContent(vacancy)
    }
}
