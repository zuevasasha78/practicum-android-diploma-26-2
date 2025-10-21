package ru.practicum.android.diploma.vacancy.domain

import ru.practicum.android.diploma.favourites.domain.db.FavouriteVacancyInteractor
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.ApiResult
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.utils.StringUtils

class VacancyInteractor(
    private val networkRepository: VacancyNetworkRepository,
    private val favouriteVacancyInteractor: FavouriteVacancyInteractor,
    private val stringUtils: StringUtils,
) {

    suspend fun getVacancy(vacancyId: String): VacancyState {
        val favoriteVacancy = favouriteVacancyInteractor.getVacancyById(vacancyId)
        if (favoriteVacancy != null) {
            return VacancyState.Content(favoriteVacancy)
        }
        return when (val result = networkRepository.getVacancy(vacancyId)) {
            is ApiResult.Success -> VacancyState.Content(result.data)
            is ApiResult.NotFound -> VacancyState.VacancyNotFound
            is ApiResult.ServerError -> VacancyState.ServerError
            is ApiResult.NoInternetConnection -> VacancyState.NoInternet
        }
    }

    fun prepareShareContent(vacancy: VacancyDetail): String = stringUtils.getShareString(vacancy)
}
