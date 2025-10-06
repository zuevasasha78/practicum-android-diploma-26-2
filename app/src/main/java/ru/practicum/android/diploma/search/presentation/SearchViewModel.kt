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
    private var currentPage = 1

    private val _screenState: MutableLiveData<SearchScreenState> = MutableLiveData(SearchScreenState.Init)
    val screenState: LiveData<SearchScreenState> get() = _screenState

    private val _canLoadNextPage = MutableLiveData(false)
    val canLoadNextPage: LiveData<Boolean> get() = _canLoadNextPage

    fun setScreenState(state: SearchScreenState) {
        _screenState.postValue(state)
    }

    fun searchVacancyDebounce(text: String) {
        if (lastSearch == text) return
        lastSearch = text
        currentPage = 1
        searchDebounce(viewModelScope) {
            searchVacancy(text, currentPage)
        }
    }

    private fun searchVacancy(text: String, page: Int) {
        if (page == 1) {
            setScreenState(SearchScreenState.Loading)
        }

        viewModelScope.launch {
            _canLoadNextPage.postValue(false)
            var isLastPage = false
            val result = searchScreenInteractor.searchVacancy(text, page)
            if (result is SearchScreenState.Success) {
                isLastPage = result.lastPage == currentPage
                if (page > 1 && _screenState.value is SearchScreenState.Success) {
                    val oldItems = (_screenState.value as SearchScreenState.Success).vacancyList
                    val newItems = oldItems + result.vacancyList
                    val newState = result.copy(vacancyList = newItems)
                    setScreenState(newState)
                } else {
                    setScreenState(result)
                }
            } else {
                setScreenState(result)
            }
            _canLoadNextPage.postValue(!isLastPage)
        }
    }

    fun loadNextPage() {
        currentPage++
        searchVacancy(lastSearch, currentPage)
    }
}
