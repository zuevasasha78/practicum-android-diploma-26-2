package ru.practicum.android.diploma.filter.domain

import ru.practicum.android.diploma.filter.domain.model.IndustriesChooserScreenState
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultFilterIndustries
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.ApiResult
import ru.practicum.android.diploma.network.domain.models.FilterIndustry
import ru.practicum.android.diploma.search.presentation.models.Placeholder

class IndustriesInteractorImpl(
    private val vacancyNetworkRepository: VacancyNetworkRepository,
    private val sharedPrefInteractor: SharedPrefInteractor
) : IndustriesInteractor {

    override suspend fun getIndustries(): IndustriesChooserScreenState {
        return when (val res = vacancyNetworkRepository.getIndustries().convertToApiResultFilterIndustries()) {
            is ApiResult.NoInternetConnection -> IndustriesChooserScreenState.Error(Placeholder.NoInternet)
            is ApiResult.Error -> IndustriesChooserScreenState.Error(Placeholder.ServerError)
            is ApiResult.Success -> {
                if (res.data.isEmpty()) {
                    IndustriesChooserScreenState.Empty
                } else {
                    IndustriesChooserScreenState.Success(
                        industries = res.data,
                        isChosen = getSelectedIndustry().id != -1
                    )
                }
            }
        }
    }

    override fun getSelectedIndustry(): FilterIndustry {
        return sharedPrefInteractor.getChosenIndustry()
    }

    override fun saveSelectedIndustry(industry: FilterIndustry) {
        sharedPrefInteractor.setIndustry(industry)
    }

    override fun clearSelectedIndustry() {
        sharedPrefInteractor.resetIndustry()
    }
}

