package ru.practicum.android.diploma.vacancy.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.vacancy.domain.VacancyInteractor
import ru.practicum.android.diploma.vacancy.domain.VacancyState

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor
) : ViewModel() {

    private val _state = MutableLiveData<VacancyState>()
    val state: LiveData<VacancyState> = _state

    private val _isFavorite = MutableLiveData(false)
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun loadVacancy(vacancyId: String) {
        _state.value = VacancyState.Loading

        viewModelScope.launch {
            val result = vacancyInteractor.getVacancy(vacancyId)
            _state.value = result
            // При загрузке вакансии устанавливаем начальное состояние избранного
            if (result is VacancyState.Content) {
                // Проверить в базе данных, добавлена ли вакансия в избранное
                _isFavorite.value = false
            }
        }
    }

    suspend fun toggleFavorite(vacancyId: String, vacancy: VacancyDetail? = null) {
        val currentState = _isFavorite.value ?: false

        if (currentState) {
            val success = vacancyInteractor.removeFromFavourite(vacancyId)
            if (success) {
                _isFavorite.value = false
            }
        } else {
            val success = vacancyInteractor.addToFavourite(vacancyId)
            if (success) {
                _isFavorite.value = true
            }
        }
    }

    fun prepareShareContent(vacancy: VacancyDetail): String {
        return vacancyInteractor.prepareShareContent(vacancy)
    }
}
