package ru.practicum.android.diploma.search.presentation.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

sealed class SearchPlaceholder(@StringRes val text: Int?, @DrawableRes val image: Int) {
    data object NoInternet : SearchPlaceholder(R.string.no_internet, R.drawable.no_internet)
    data object ServerErrorSearch : SearchPlaceholder(R.string.server_error, R.drawable.server_error_search)
    data object SearchInitPlaceholder : SearchPlaceholder(
        null,
        R.drawable.search_screen_init_placeholder
    )

    data object NoSearchResult :
        SearchPlaceholder(
            R.string.no_search_result_placeholder_text,
            R.drawable.no_result_placeholder
        )
}
