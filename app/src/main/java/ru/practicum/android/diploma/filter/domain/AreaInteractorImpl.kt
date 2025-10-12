package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.presentation.workplace.AreaScreenState
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToWorkplace
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository

class AreaInteractorImpl(private val networkRepository: VacancyNetworkRepository) : AreaInteractor {

    override suspend fun getCountries(): AreaScreenState {
        val result = networkRepository.getAreas()
        return when (result) {
            is ApiResult.Error<*> -> AreaScreenState.Error
            ApiResult.NoInternetConnection -> AreaScreenState.Error
            is ApiResult.Success<List<FilterArea>> -> {
                if (result.data.isEmpty()) {
                    AreaScreenState.Empty
                } else {
                    AreaScreenState.Content(result.data.convertToWorkplace())
                }
            }
        }
    }

    override suspend fun getRegions(name: String?): AreaScreenState {
        val result = networkRepository.getAreas()
        return when (result) {
            is ApiResult.Error<*> -> AreaScreenState.Error
            ApiResult.NoInternetConnection -> AreaScreenState.Error
            is ApiResult.Success<List<FilterArea>> -> {
                if (result.data.isEmpty()) {
                    AreaScreenState.Empty
                } else {
                    if (name != null) {
                        val data = result.data.first {
                            it.name == name
                        }.areas
                        AreaScreenState.Content(data.convertToWorkplace())
                    } else {
                        val data = result.data.flatMap { it.areas }
                        AreaScreenState.Content(data.convertToWorkplace())
                    }
                }
            }
        }
    }

    override suspend fun getRegionsByName(name: String): AreaScreenState {
        val result = networkRepository.getAreas()
        return when (result) {
            is ApiResult.Error<*> -> AreaScreenState.Error
            ApiResult.NoInternetConnection -> AreaScreenState.Error
            is ApiResult.Success<List<FilterArea>> -> {
                if (result.data.isEmpty()) {
                    AreaScreenState.Empty
                } else {
                    val data = result.data.flatMap { it.areas }.filter { it.name.lowercase().contains(name) }
                    if (data.isEmpty()) {
                        AreaScreenState.Empty
                    } else {
                        AreaScreenState.Content(data.convertToWorkplace())
                    }
                }
            }
        }
    }
}
