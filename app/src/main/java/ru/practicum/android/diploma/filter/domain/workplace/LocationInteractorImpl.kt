package ru.practicum.android.diploma.filter.domain.workplace

import ru.practicum.android.diploma.filter.domain.model.AreaResult
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.ApiResult

class LocationInteractorImpl(private val networkRepository: VacancyNetworkRepository) : LocationInteractor {

    override suspend fun getCountries(): AreaResult {
        return when (val result = networkRepository.getAreas()) {
            is ApiResult.ServerError -> AreaResult.Error
            is ApiResult.NoInternetConnection -> AreaResult.NoInternetConnection
            is ApiResult.NotFound -> AreaResult.Empty
            is ApiResult.Success -> AreaResult.Success(result.data.filter { it.parent == null })
        }
    }

    override suspend fun getRegionsByName(name: String, id: Int?): AreaResult {
        return when (val result = networkRepository.getAreas()) {
            is ApiResult.ServerError -> AreaResult.Error
            is ApiResult.NoInternetConnection -> AreaResult.NoInternetConnection
            is ApiResult.NotFound -> AreaResult.Empty
            is ApiResult.Success -> {
                val filterData = result.data
                    .filter { it.parent != null }
                    .filter { id == null || it.parent?.id == id }
                    .filter { it.name.lowercase().contains(name.lowercase()) }
                AreaResult.Success(filterData)
            }
        }
    }

    override suspend fun getRegionsById(id: Int?): AreaResult {
        return when (val result = networkRepository.getAreas()) {
            is ApiResult.ServerError -> AreaResult.Error
            is ApiResult.NoInternetConnection -> AreaResult.NoInternetConnection
            is ApiResult.NotFound -> AreaResult.Empty
            is ApiResult.Success -> {
                val filterData = result.data
                    .filter { it.parent != null }
                    .filter { id == null || it.parent?.id == id }
                AreaResult.Success(filterData)
            }
        }
    }
}
