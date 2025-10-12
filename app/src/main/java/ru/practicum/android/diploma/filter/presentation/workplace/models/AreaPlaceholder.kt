package ru.practicum.android.diploma.filter.presentation.workplace.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.practicum.android.diploma.R

sealed class AreaPlaceholder(@StringRes val text: Int?, @DrawableRes val image: Int) {
    data object Error : AreaPlaceholder(R.string.cant_get_area_list, R.drawable.no_area)
    data object Empty : AreaPlaceholder(R.string.area_not_found, R.drawable.no_result_placeholder)
}
