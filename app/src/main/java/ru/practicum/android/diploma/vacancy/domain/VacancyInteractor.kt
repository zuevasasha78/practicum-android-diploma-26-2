package ru.practicum.android.diploma.vacancy.domain

import android.util.Log
import ru.practicum.android.diploma.db.domain.VacancyDbRepository
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToVacancyDetail
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.utils.StringUtils
import java.net.SocketTimeoutException

class VacancyInteractor(
    private val vacancyRepository: VacancyDbRepository,
    private val networkRepository: VacancyNetworkRepository,
    private val stringUtils: StringUtils
) {

    suspend fun getVacancy(vacancyId: String): VacancyState {
        return try {
            return when (val result = networkRepository.getVacancy(vacancyId)) {
                is ApiResult.Success -> {
                    val vacancyDetail = result.data.convertToVacancyDetail()
                    VacancyState.Content(vacancyDetail)
                }

                is ApiResult.Error -> {
                    if (result.code == CODE_404) {
                        VacancyState.VacancyNotFound
                    } else {
                        VacancyState.ServerError
                    }
                }
                is ApiResult.NoInternetConnection -> VacancyState.NoInternet
            }
        } catch (e: SocketTimeoutException) {
            Log.e("VacancyInteractor", "Unknown error for vacancy $vacancyId", e)
            VacancyState.ServerError
        }
    }


    suspend fun removeFromFavourite(vacancyId: String): Boolean {
        return runCatching {
            vacancyRepository.deleteVacancy(vacancyId)
        }.onFailure { e ->
            Log.e("MyLog", "Failed to remove vacancy $vacancyId from favourites", e)
        }.isSuccess
    }

    suspend fun addToFavourite(vacancy: VacancyDetail): Boolean {
        return runCatching {
            true // временная заглушка
        }.onFailure { e ->
            Log.e("MyLog", "Failed to add vacancy ${vacancy.id} to favourites", e)
        }.isSuccess
    }

    suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        // Временная реализация - нужно получить все избранные и проверить наличие
        return false
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
