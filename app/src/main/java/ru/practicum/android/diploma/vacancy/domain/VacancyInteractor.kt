package ru.practicum.android.diploma.vacancy.domain

import android.util.Log
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.db.domain.VacancyRepository
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.dto.response.Salary
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.vacancy.domain.model.VacancyModel

class VacancyInteractor(
    private val vacancyRepository: VacancyRepository,
    private val networkRepository: ru.practicum.android.diploma.network.domain.VacancyRepository
) {

    suspend fun getVacancy(vacancyId: String): VacancyState {
        return when (val result = networkRepository.getVacancy(vacancyId)) {
            is ApiResult.Success -> {
                VacancyState.Content(convertToVacancyModel(result.data))
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

    fun prepareShareContent(vacancy: VacancyModel): String {
        return buildString {
            append("Вакансия: ${vacancy.name}\n")
            append("Компания: ${vacancy.employerName}\n")
            append("Зарплата: ${vacancy.salary}\n")
            append("Город: ${vacancy.area}\n")
            append("Ссылка: ${vacancy.url}")
        }
    }

    private fun convertToVacancyModel(vacancyDetail: VacancyDetail): VacancyModel {
        return VacancyModel(
            id = vacancyDetail.id,
            name = vacancyDetail.name,
            salary = formatSalary(vacancyDetail.salary),
            employerName = vacancyDetail.employer.name,
            employerLogoUrl = vacancyDetail.employer.logo,
            area = vacancyDetail.area.name,
            address = vacancyDetail.address?.city ?: vacancyDetail.area.name,
            experience = vacancyDetail.experience.name,
            employment = vacancyDetail.employment.name,
            description = vacancyDetail.description,
            responsibilities = vacancyDetail.description,
            requirements = vacancyDetail.description,
            conditions = vacancyDetail.description,
            skills = vacancyDetail.skills,
            url = vacancyDetail.url
        )
    }

    private fun formatSalary(salary: Salary): String {
        return when {
            salary.from != null && salary.to != null -> "от ${salary.from} до ${salary.to} ${salary.currency ?: ""}"
            salary.from != null -> "от ${salary.from} ${salary.currency ?: ""}"
            salary.to != null -> "до ${salary.to} ${salary.currency ?: ""}"
            else -> R.string.salary_not_specified.toString()
        }.trim()
    }

    companion object {
        private const val CODE_404 = 404
    }
}
