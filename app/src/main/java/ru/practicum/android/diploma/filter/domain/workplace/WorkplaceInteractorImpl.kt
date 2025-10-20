package ru.practicum.android.diploma.filter.domain.workplace

import ru.practicum.android.diploma.filter.domain.WorkplaceRepository
import ru.practicum.android.diploma.filter.domain.model.Workplace

class WorkplaceInteractorImpl(private val workplaceRepository: WorkplaceRepository) : WorkplaceInteractor {

    override suspend fun getWorkplace(): Workplace {
        val country = workplaceRepository.getCountry()
        val region = workplaceRepository.getRegion()
        return Workplace(country, region)
    }

    override suspend fun saveWorkplace(workplace: Workplace) {
        workplaceRepository.saveWorkplace(workplace)
    }

    override suspend fun clearWorkplace() {
        workplaceRepository.clearWorkplace()
    }

    override suspend fun getPlaceId(): Int? {
        val region = workplaceRepository.getRegion()
        if (region != null) {
            return region.id
        }
        val country = workplaceRepository.getCountry()
        return if (country != null) {
            country.id
        } else {
            null
        }
    }
}
