package ru.practicum.android.diploma.network.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

sealed class Errors(@StringRes val text: Int, @DrawableRes val image: Int) {
    data object NoInternet : Errors(R.string.no_internet, R.drawable.no_internet)
    data object ServerErrorSearch : Errors(R.string.server_error, R.drawable.server_error_search)
    data object ServerErrorVacancy : Errors(R.string.server_error, R.drawable.server_error_vacancy)
}
