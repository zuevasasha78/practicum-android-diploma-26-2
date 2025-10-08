package ru.practicum.android.diploma.favourites.presentation.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

sealed class FavoritePlaceholder(@StringRes val text: Int?, @DrawableRes val image: Int) {
    data object Error :
        FavoritePlaceholder(R.string.no_search_result_placeholder_text, R.drawable.no_result_placeholder)

    data object EmptyList : FavoritePlaceholder(R.string.empty_favourite_list, R.drawable.empty_favourite_list)
}
