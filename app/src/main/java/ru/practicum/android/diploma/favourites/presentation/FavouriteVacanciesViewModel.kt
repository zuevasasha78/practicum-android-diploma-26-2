package ru.practicum.android.diploma.favourites.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favourites.domain.db.FavouriteVacancyInteractor
import ru.practicum.android.diploma.search.domain.model.Vacancy

class FavouriteVacanciesViewModel(
    private val favouriteVacancyInteractor: FavouriteVacancyInteractor
) : ViewModel() {
    private val _screenState: MutableLiveData<FavouriteScreenState> = MutableLiveData<FavouriteScreenState>(
        FavouriteScreenState.Loading
    )

    val screenState: LiveData<FavouriteScreenState> = _screenState

    fun uploadFavoriteVacancy() {
        viewModelScope.launch {
            favouriteVacancyInteractor.getFavouriteVacancies()
                .distinctUntilChanged()
                .catch { renderState(FavouriteScreenState.Error) }
                .collect { processResult(it) }
        }
    }

    private fun renderState(favouriteScreenState: FavouriteScreenState) {
        _screenState.value = favouriteScreenState
    }

    private fun processResult(list: List<Vacancy>?) {
        when {
            list == null -> renderState(FavouriteScreenState.Error)
            list.isEmpty() -> renderState(FavouriteScreenState.Empty)
            else -> renderState(FavouriteScreenState.Content(list))
        }
    }
}
