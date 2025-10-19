package ru.practicum.android.diploma.filter.presentation.workplace.models

import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.filter.domain.model.Workplace

object WorkplaceConvertor {

    fun Workplace.convertToWorkplaceUi(): WorkplaceUi {
        return WorkplaceUi(
            country = this.country?.convertToLocationUi(),
            region = this.region?.convertToLocationUi()
        )
    }

    fun Location.convertToLocationUi(): LocationUi {
        return LocationUi(
            id = this.id,
            name = this.name,
            parent = this.parent?.convertToLocationUi()
        )
    }

    fun WorkplaceUi.convertToWorkplace(): Workplace {
        return Workplace(
            country = this.country?.convertToLocation(),
            region = this.region?.convertToLocation()
        )
    }

    fun LocationUi.convertToLocation(): Location {
        return Location(
            id = this.id,
            name = this.name,
            parent = this.parent?.convertToLocation()
        )
    }
}
