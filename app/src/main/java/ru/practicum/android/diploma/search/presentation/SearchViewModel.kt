package ru.practicum.android.diploma.search.presentation

import android.accessibilityservice.AccessibilityService.ScreenshotResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.search.domain.SearchScreenInteractor
import ru.practicum.android.diploma.search.domain.SearchScreenState
import ru.practicum.android.diploma.utils.DebounceUtils.searchDebounce
import ru.practicum.android.diploma.utils.Utils.map

class SearchViewModel(val searchScreenInteractor: SearchScreenInteractor) : ViewModel() {

    private val screenState: MutableLiveData<SearchScreenState> = MutableLiveData(SearchScreenState.Init)
    fun getScreenState(): LiveData<SearchScreenState> = screenState

    fun setScreenState(state: SearchScreenState) {
        screenState.postValue(state)
    }

    fun searchVacancyDebounce(text: String) {
        searchDebounce(viewModelScope) {
            searchVacancy(text)
        }
    }

    private fun searchVacancy(text: String) {
        setScreenState(SearchScreenState.Loading)
        viewModelScope.launch {
            when (val result = searchScreenInteractor.searchVacancy(text)) {
                is ApiResult.Success -> setScreenState(SearchScreenState.Success(result.data.found ,result.data.items.map { it.map() }))
                is ApiResult.NoInternetConnection -> setScreenState(SearchScreenState.NoInternet)
                is ApiResult.Error -> setScreenState(SearchScreenState.Error)
            }
        }
    }
}
