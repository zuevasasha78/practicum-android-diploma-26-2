package ru.practicum.android.diploma.filter.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

sealed class IndustriesPlaceholder(@StringRes val text: Int?, @DrawableRes val image: Int) {
    data object NoInternet : IndustriesPlaceholder(R.string.no_internet, R.drawable.no_internet)
    data object ServerError : IndustriesPlaceholder(R.string.server_error, R.drawable.server_error_search)
    data object NoResult :
        IndustriesPlaceholder(
            R.string.industry_not_found,
            R.drawable.no_result_placeholder
        )
}
