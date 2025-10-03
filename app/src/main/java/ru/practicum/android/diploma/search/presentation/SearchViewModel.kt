package ru.practicum.android.diploma.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.search.domain.SearchScreenInteractor
import ru.practicum.android.diploma.search.domain.SearchScreenState

class SearchViewModel(val searchScreenInteractor: SearchScreenInteractor): ViewModel() {

    private val screenState: MutableLiveData<SearchScreenState> = MutableLiveData(SearchScreenState.Init)
    fun getScreenState(): LiveData<SearchScreenState> = screenState

}
