package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.presentation.workplace.AreaScreenState
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultFilterArea
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToWorkplace
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.ApiResult

class AreaInteractorImpl(private val networkRepository: VacancyNetworkRepository) : AreaInteractor {

    override suspend fun getCountries(): AreaScreenState {
        return when(val res = networkRepository.getAreas().convertToApiResultFilterArea()) {
            is ApiResult.ServerError -> AreaScreenState.Error
            is ApiResult.NoInternetConnection -> AreaScreenState.Error
            is ApiResult.Success -> AreaScreenState.Content(res.data.convertToWorkplace())
            is ApiResult.NotFound -> AreaScreenState.Empty
        }
    }

    override suspend fun getRegions(name: String?): AreaScreenState {
        return when(val res = networkRepository.getAreas().convertToApiResultFilterArea()) {
            is ApiResult.ServerError -> AreaScreenState.Error
            is ApiResult.NoInternetConnection -> AreaScreenState.Error
            is ApiResult.Success -> {
                if (name != null) {
                    val data = res.data.first {
                        it.name == name
                    }.areas
                    AreaScreenState.Content(data.convertToWorkplace())
                } else {
                    val data = res.data.flatMap { it.areas }
                    AreaScreenState.Content(data.convertToWorkplace())
                }
            }
            is ApiResult.NotFound -> AreaScreenState.Empty
        }
    }

    override suspend fun getRegionsByName(name: String): AreaScreenState {
        return when(val res = networkRepository.getAreas().convertToApiResultFilterArea()) {
            is ApiResult.ServerError -> AreaScreenState.Error
            is ApiResult.NoInternetConnection -> AreaScreenState.Error
            is ApiResult.Success -> {
                val data = res.data.flatMap { it.areas }.filter { it.name.lowercase().contains(name) }
                if (data.isEmpty()) {
                    AreaScreenState.Empty
                } else {
                    AreaScreenState.Content(data.convertToWorkplace())
                }
            }
            is ApiResult.NotFound -> AreaScreenState.Empty
        }
    }
}
