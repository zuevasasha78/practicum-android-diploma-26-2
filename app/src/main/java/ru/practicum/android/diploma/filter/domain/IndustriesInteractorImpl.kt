package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToFilterIndustry
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.search.presentation.models.Placeholder

class IndustriesInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository
) : IndustriesInteractor {

    override suspend fun getIndustries(): IndustriesChooserScreenState {
        return when (val res = vacancyNetworkRepository.getIndustries()) {
            is ApiResult.NoInternetConnection -> IndustriesChooserScreenState.Error(Placeholder.NoInternet)
            is ApiResult.Error -> IndustriesChooserScreenState.Error(Placeholder.ServerError)
            is ApiResult.Success -> {
                IndustriesChooserScreenState.Success(res.data.map { it.convertToFilterIndustry() })
            }
        }
    }
}
