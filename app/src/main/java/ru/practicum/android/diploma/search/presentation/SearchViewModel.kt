package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.SharedPrefInteractor
import ru.practicum.android.diploma.search.domain.SearchScreenInteractor
import ru.practicum.android.diploma.search.domain.models.PaginationState
import ru.practicum.android.diploma.search.domain.models.SearchScreenState
import ru.practicum.android.diploma.search.presentation.models.Placeholder
import ru.practicum.android.diploma.utils.DebounceUtils.searchDebounce

class SearchViewModel(
    private val searchScreenInteractor: SearchScreenInteractor,
    private val sharedPrefInteractor: SharedPrefInteractor
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
            val industry = sharedPrefInteractor.getChosenIndustry()
            val salary = sharedPrefInteractor.getSalary()
            val onlyWithSalary = sharedPrefInteractor.getOnlyWithSalary()

            val result = searchScreenInteractor.searchVacancy(
                text = text,
                page = page,
                industry = if (industry.id != -1) {
                    industry.id
                } else {
                    null
                },
                salary = salary,
                onlyWithSalary = onlyWithSalary
            )
            if (result is SearchScreenState.Success) {
                isLastPage = result.lastPage == currentPage
                loadNewItems(page, result)
            } else {
                if (page > 1 && _screenState.value is SearchScreenState.Success) {
                    val current = _screenState.value as SearchScreenState.Success
                    val errorMessage = when ((result as SearchScreenState.Error).placeholder) {
                        is Placeholder.NoInternet -> R.string.check_internet_connection
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

    private fun loadNewItems(page: Int, result: SearchScreenState.Success) {
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

    private fun getCurrentFilters(): Map<String, String> {
        val filters = mutableMapOf<String, String>()

        val industry = sharedPrefInteractor.getChosenIndustry()
        if (industry.id != -1) {
            filters["industry"] = industry.id.toString()
        }

        val salary = sharedPrefInteractor.getSalary()
        if (salary.isNotBlank()) {
            filters["salary"] = salary
        }

        if (sharedPrefInteractor.getOnlyWithSalary()) {
            filters["only_with_salary"] = "true"
        }

        return filters
    }
}
