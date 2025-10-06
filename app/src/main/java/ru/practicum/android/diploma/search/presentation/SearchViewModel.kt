package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.domain.SearchScreenInteractor
import ru.practicum.android.diploma.search.domain.SearchScreenState
import ru.practicum.android.diploma.utils.DebounceUtils.searchDebounce

class SearchViewModel(private val searchScreenInteractor: SearchScreenInteractor) : ViewModel() {

    private var lastSearch = ""
    private val screenState: MutableLiveData<SearchScreenState> = MutableLiveData(SearchScreenState.Init)
    fun getScreenState(): LiveData<SearchScreenState> = screenState

    fun setScreenState(state: SearchScreenState) {
        screenState.postValue(state)
    }

    fun searchVacancyDebounce(text: String) {
        if (lastSearch == text) {
            return
        }
        lastSearch = text
        searchDebounce(viewModelScope) {
            searchVacancy(text)
        }
    }

    private fun searchVacancy(text: String) {
        setScreenState(SearchScreenState.Loading)
        viewModelScope.launch {
            setScreenState(searchScreenInteractor.searchVacancy(text))
        }
    }
}
