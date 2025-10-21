package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.filter.domain.model.IndustriesPlaceholder
import ru.practicum.android.diploma.search.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.search.domain.model.ApiResult
import ru.practicum.android.diploma.search.domain.model.FilterIndustry

class IndustriesInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository,
    private val sharedPrefInteractor: SharedPrefInteractor
) : IndustriesInteractor {

    override suspend fun getIndustries(): IndustriesChooserScreenState {
        return when (val res = vacancyNetworkRepository.getIndustries()) {
            is ApiResult.NoInternetConnection -> IndustriesChooserScreenState.Error(IndustriesPlaceholder.NoInternet)
            is ApiResult.ServerError -> IndustriesChooserScreenState.Error(IndustriesPlaceholder.ServerError)
            is ApiResult.NotFound -> IndustriesChooserScreenState.Error(IndustriesPlaceholder.NoResult)
            is ApiResult.Success -> {
                IndustriesChooserScreenState.Success(
                    industries = res.data,
                    isChosen = getSelectedIndustry().id != -1
                )
            }
        }
    }

    override suspend fun getSelectedIndustry(): FilterIndustry {
        return sharedPrefInteractor.getChosenIndustry()
    }

    override suspend fun saveSelectedIndustry(industry: FilterIndustry) {
        sharedPrefInteractor.setIndustry(industry)
    }

    override suspend fun clearSelectedIndustry() {
        sharedPrefInteractor.resetIndustry()
    }
}
