package ru.practicum.android.diploma.vacancy.domain

import android.util.Log
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.db.domain.VacancyDbRepository
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.VacancyNetworkConvertor.convertToVacancyDetail
import ru.practicum.android.diploma.network.domain.VacancyNetworkRepository
import ru.practicum.android.diploma.network.domain.models.Salary
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

class VacancyInteractor(
    private val vacancyRepository: VacancyDbRepository,
    private val networkRepository: VacancyNetworkRepository
) {

    suspend fun getVacancy(vacancyId: String): VacancyState {
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

    suspend fun getTestVacancy(): VacancyDetail {
        return VacancyDetail(
            id = "test_123",
            name = "Тестовая вакансия",
            salary = Salary(100000, 200000, "RUR"),
            employerName = "Тестовая компания",
            employerLogoUrl = null,
            area = "Москва",
            address = "Москва, ул. Тестовая",
            experience = "От 1 года до 3 лет",
            employment = "Полная занятость",
            description = "Тестовое описание вакансии",
            responsibilities = "Тестовые обязанности",
            requirements = "Тестовые требования",
            conditions = "Тестовые условия",
            skills = listOf("Kotlin", "Android SDK", "Git"),
            url = "https://example.com/vacancy/test"
        )
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
            append("Зарплата: ${formatSalary(vacancy.salary)}\n")
            append("Город: ${vacancy.area}\n")
            append("Ссылка: ${vacancy.url}")
        }
    }

    private fun formatSalary(salary: Salary): String {
        return when {
            salary.from != null && salary.to != null -> "от ${salary.from} до ${salary.to} ${salary.currency ?: ""}"
            salary.from != null -> "от ${salary.from} ${salary.currency ?: ""}"
            salary.to != null -> "до ${salary.to} ${salary.currency ?: ""}"
            else -> "Зарплата не указана"
        }.trim()
    }

    companion object {
        private const val CODE_404 = 404
    }
}
