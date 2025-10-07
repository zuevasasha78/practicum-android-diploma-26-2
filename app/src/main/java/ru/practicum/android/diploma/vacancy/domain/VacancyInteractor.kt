package ru.practicum.android.diploma.vacancy.domain

import android.util.Log
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
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
            // Сначала проверяем в избранном
            val favoriteVacancy = vacancyRepository.getVacancyById(vacancyId)
            if (favoriteVacancy != null) {
                VacancyState.Content(favoriteVacancy)
            } else {
                // Если нет в избранном, загружаем из сети
                loadVacancyFromNetwork(vacancyId)
            }
        } catch (e: SocketTimeoutException) {
            Log.e("VacancyInteractor", "Socket timeout for vacancy $vacancyId", e)
            VacancyState.ServerError
        }
    }

    private suspend fun loadVacancyFromNetwork(vacancyId: String): VacancyState {
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
    }

    suspend fun removeFromFavourite(vacancyId: String): Boolean {
        return runCatching {
            vacancyRepository.deleteVacancy(vacancyId)
        }.onFailure { e ->
            Log.e("MyLog", "Failed to remove vacancy $vacancyId from favourites", e)
        }.isSuccess
    }

    suspend fun addToFavourite(vacancy: VacancyEntity): Boolean {
        return runCatching {
            vacancyRepository.addVacancy(vacancy)
            true
        }.onFailure { e ->
            Log.e("MyLog", "Failed to add vacancy ${vacancy.id} to favourites", e)
        }.isSuccess
    }

    suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return vacancyRepository.getVacancyById(vacancyId) != null
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
