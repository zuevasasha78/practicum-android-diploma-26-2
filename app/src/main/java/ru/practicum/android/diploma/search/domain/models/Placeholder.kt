package ru.practicum.android.diploma.search.domain.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

sealed class Placeholder(@StringRes val text: Int?, @DrawableRes val image: Int) {
    data object NoInternet : Placeholder(R.string.no_internet, R.drawable.no_internet)
    data object ServerError : Placeholder(R.string.server_error, R.drawable.server_error_search)
    data object InitPlaceholder : Placeholder(
        null,
        R.drawable.search_screen_init_placeholder
    )

    data object NoResult :
        Placeholder(
            R.string.no_search_result_placeholder_text,
            R.drawable.no_result_placeholder
        )
}
