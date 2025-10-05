package ru.practicum.android.diploma.vacancy.domain

import android.content.Context
import android.util.Log
import ru.practicum.android.diploma.db.domain.VacancyDbRepository
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.utils.Utils.convertToVacancyModel

class VacancyInteractor(
    private val vacancyRepository: VacancyDbRepository,
    private val networkRepository: VacancyNetworkRepository
) {

    suspend fun getVacancy(vacancyId: String, context: Context): VacancyState {
        return when (val result = networkRepository.getVacancy(vacancyId)) {
            is ApiResult.Success -> {
                VacancyState.Content(result.data.convertToVacancyModel(context))
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

    fun addToFavourite(vacancyId: String): Boolean {
        return vacancyId.isEmpty() // Заглушка - просто возвращаем true
    }

    fun prepareShareContent(vacancy: VacancyDetail): String {
        return buildString {
            append("Вакансия: ${vacancy.name}\n")
            append("Компания: ${vacancy.employerName}\n")
            append("Зарплата: ${vacancy.salary}\n")
            append("Город: ${vacancy.area}\n")
            append("Ссылка: ${vacancy.url}")
        }
    }

    companion object {
        private const val CODE_404 = 404
    }
}
