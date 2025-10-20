package ru.practicum.android.diploma.filter.domain.workplace

import ru.practicum.android.diploma.filter.domain.model.AreaResult
import ru.practicum.android.diploma.network.data.ApiResultDto
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToLocation
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository

class LocationInteractorImpl(private val networkRepository: VacancyNetworkRepository) : LocationInteractor {

    override suspend fun getCountries(): AreaResult {
        val result = networkRepository.getAreas()
        return when (result) {
            is ApiResultDto.Error<*> -> AreaResult.Error
            ApiResultDto.NoInternetConnection -> AreaResult.NoInternetConnection
            is ApiResultDto.Success<List<FilterArea>> -> {
                if (result.data.isEmpty()) {
                    AreaResult.Empty
                } else {
                    AreaResult.Success(result.data.convertToLocation().filter { it.parent == null })
                }
            }
        }
    }

    override suspend fun getRegionsByName(name: String, id: Int?): AreaResult {
        val result = networkRepository.getAreas()
        return when (result) {
            is ApiResultDto.Error<*> -> AreaResult.Error
            ApiResultDto.NoInternetConnection -> AreaResult.Error
            is ApiResultDto.Success<List<FilterArea>> -> {
                if (result.data.isEmpty()) {
                    AreaResult.Empty
                } else {
                    val filterData = result.data.convertToLocation()
                        .filter { it.parent != null }
                        .filter { id == null || it.parent?.id == id }
                        .filter { it.name.lowercase().contains(name.lowercase()) }
                    AreaResult.Success(filterData)
                }
            }
        }
    }

    override suspend fun getRegionsById(id: Int?): AreaResult {
        val result = networkRepository.getAreas()
        return when (result) {
            is ApiResultDto.Error<*> -> AreaResult.Error
            ApiResultDto.NoInternetConnection -> AreaResult.Error
            is ApiResultDto.Success<List<FilterArea>> -> {
                if (result.data.isEmpty()) {
                    AreaResult.Empty
                } else {
                    val filterData = result.data.convertToLocation()
                        .filter { it.parent != null }
                        .filter { id == null || it.parent?.id == id }
                    AreaResult.Success(filterData)
                }
            }
        }
    }
}
