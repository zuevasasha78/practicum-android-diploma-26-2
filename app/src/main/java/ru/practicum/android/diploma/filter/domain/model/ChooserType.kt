package ru.practicum.android.diploma.filter.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ChooserType : Parcelable {
    data object TownType : ChooserType()
    data object CountryType : ChooserType()
    data object SectorType : ChooserType()
}
