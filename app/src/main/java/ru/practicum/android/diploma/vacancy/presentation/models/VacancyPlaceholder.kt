package ru.practicum.android.diploma.vacancy.presentation.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

sealed class VacancyPlaceholder(@StringRes val text: Int?, @DrawableRes val image: Int) {
    data object NoInternet : VacancyPlaceholder(R.string.no_internet, R.drawable.no_internet)
    data object ServerError : VacancyPlaceholder(R.string.server_error, R.drawable.server_error_vacancy)
    data object VacancyNotFound :
        VacancyPlaceholder(
            R.string.vacancy_not_found,
            R.drawable.placeholder_vacancy_not_found
        )
}
