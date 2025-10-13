package ru.practicum.android.diploma.vacancy.domain

import android.util.Log
import ru.practicum.android.diploma.db.domain.interactor.FavouriteVacancyInteractor
import ru.practicum.android.diploma.network.data.ApiResultDto
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToVacancyDetail
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
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
            when (val result = networkRepository.getVacancy(vacancyId)) {
                is ApiResultDto.Success -> {
                    val vacancyDetail = result.data.convertToVacancyDetail()
                    VacancyState.Content(vacancyDetail)
                }

                is ApiResultDto.Error -> {
                    if (result.code == CODE_404) {
                        VacancyState.VacancyNotFound
                    } else {
                        VacancyState.ServerError
                    }
                }
                is ApiResultDto.NoInternetConnection -> VacancyState.NoInternet
            }
        } catch (e: SocketTimeoutException) {
            Log.e("VacancyInteractor", "Unknown error for vacancy $vacancyId", e)
            VacancyState.ServerError
        }
    }

    fun prepareShareContent(vacancy: VacancyDetail): String {
        return buildString {
            append("Вакансия: ${vacancy.name}\n")
            append("Компания: ${vacancy.employerName}\n")
            append("Зарплата: ${stringUtils.getSalaryString(vacancy.salary)}\n")
            append("Город: ${vacancy.area}\n")
            append("Ссылка: ${vacancy.url}")
        }
    }

    companion object {
        private const val CODE_404 = 404
    }
}
