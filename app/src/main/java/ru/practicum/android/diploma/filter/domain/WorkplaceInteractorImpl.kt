package ru.practicum.android.diploma.filter.domain

class WorkplaceInteractorImpl : WorkplaceInteractor {

    override suspend fun getWorkplace(regionValue: String?, countryValue: String?): List<Workplace> {
        return listOf(
            Workplace(value = countryValue, type = WorkplaceType.COUNTRY, isMainSelector = true),
            Workplace(value = regionValue, type = WorkplaceType.REGION, isMainSelector = true),
        )
    }
}
