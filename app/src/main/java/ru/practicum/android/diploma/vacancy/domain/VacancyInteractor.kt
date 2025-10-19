package ru.practicum.android.diploma.vacancy.domain

import ru.practicum.android.diploma.db.domain.interactor.FavouriteVacancyInteractor
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToApiResultVacancyDetail
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.network.domain.models.ApiResult
import ru.practicum.android.diploma.utils.StringUtils
import java.net.SocketTimeoutException

class VacancyInteractor(
    private val networkRepository: VacancyNetworkRepository,
    private val favouriteVacancyInteractor: FavouriteVacancyInteractor,
    private val stringUtils: StringUtils,
) {

    suspend fun getVacancy(vacancyId: String): VacancyState {
        return try {
            val favoriteVacancy = favouriteVacancyInteractor.getVacancyById(vacancyId)
            if (favoriteVacancy != null) {
                return VacancyState.Content(favoriteVacancy)
            }
            when (val result = networkRepository.getVacancy(vacancyId).convertToApiResultVacancyDetail()) {
                is ApiResult.Success -> VacancyState.Content(result.data)
                is ApiResult.NotFound -> VacancyState.VacancyNotFound
                is ApiResult.ServerError -> VacancyState.ServerError
                is ApiResult.NoInternetConnection -> VacancyState.NoInternet
            }
        } catch (e: SocketTimeoutException) {
            VacancyState.ServerError
        }
    }

    fun prepareShareContent(vacancy: VacancyDetail): String = stringUtils.getShareString(vacancy)
}
