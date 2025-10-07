package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.SearchScreenInteractor
import ru.practicum.android.diploma.search.domain.models.PaginationState
import ru.practicum.android.diploma.search.domain.models.SearchScreenState
import ru.practicum.android.diploma.search.presentation.models.SearchPlaceholder
import ru.practicum.android.diploma.utils.DebounceUtils.searchDebounce

class SearchViewModel(
    private val searchScreenInteractor: SearchScreenInteractor
) : ViewModel() {

    private var lastSearch = ""
    private var currentPage = 1

    private val _screenState: MutableLiveData<SearchScreenState> =
        MutableLiveData(SearchScreenState.Init)
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
                    val current = _screenState.value as SearchScreenState.Success
                    val newItems = current.vacancyList + result.vacancyList
                    val newState = result.copy(
                        vacancyList = newItems,
                        paginationState = PaginationState.Idle
                    )
                    setScreenState(newState)
                } else {
                    setScreenState(result.copy(paginationState = PaginationState.Idle))
                }
            } else {
                if (page > 1 && _screenState.value is SearchScreenState.Success) {
                    val current = _screenState.value as SearchScreenState.Success
                    val errorMessage = when ((result as SearchScreenState.Error).placeholder) {
                        is SearchPlaceholder.NoInternet -> R.string.check_internet_connection
                        else -> R.string.error_occurred
                    }
                    setScreenState(
                        current.copy(paginationState = PaginationState.Error(errorMessage))
                    )
                } else {
                    setScreenState(result)
                }
            }

            _canLoadNextPage.postValue(!isLastPage)
        }
    }

    fun loadNextPage() {
        val currentState = _screenState.value
        if (currentState !is SearchScreenState.Success ||
            currentState.paginationState == PaginationState.Loading
        ) {
            return
        }

        currentPage++

        setScreenState(currentState.copy(paginationState = PaginationState.Loading))

        searchVacancy(lastSearch, currentPage)
    }
}
