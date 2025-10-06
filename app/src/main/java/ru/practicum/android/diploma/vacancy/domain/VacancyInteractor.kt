package ru.practicum.android.diploma.vacancy.domain

import android.util.Log
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.db.domain.VacancyRepository
import ru.practicum.android.diploma.network.data.ApiResult
import ru.practicum.android.diploma.network.data.dto.response.Contacts
import ru.practicum.android.diploma.network.data.dto.response.Salary
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail
import ru.practicum.android.diploma.vacancy.domain.model.VacancyModel

class VacancyInteractor(
    private val vacancyRepository: VacancyRepository,
    private val networkRepository: ru.practicum.android.diploma.network.domain.VacancyRepository
) {

    suspend fun getVacancy(vacancyId: String): VacancyState {
        // Для тестирования - временная заглушка
        return if (vacancyId == "test") {
            VacancyState.Content(createTestVacancy())
        } else {
            when (val result = networkRepository.getVacancy(vacancyId)) {
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
    }

    suspend fun removeFromFavourite(vacancyId: String): Boolean {
        return runCatching {
            vacancyRepository.deleteVacancy(vacancyId)
        }.onFailure { e ->
            Log.e("MyLog", "Failed to remove vacancy $vacancyId from favourites", e)
        }.isSuccess
    }

    fun addToFavourite(vacancyId: String): Boolean {
        return vacancyId.isNotEmpty() // Заглушка - просто возвращаем true
    }

    fun prepareShareContent(vacancy: VacancyModel): String {
        return buildString {
            append("Вакансия: ${vacancy.name}\n")
            append("Компания: ${vacancy.employerName}\n")
            append("Зарплата: ${vacancy.salary ?: "не указана"}\n")
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
            address = vacancyDetail.address?.city,
            experience = vacancyDetail.experience.name,
            employment = vacancyDetail.employment.name,
            description = vacancyDetail.description,
            responsibilities = extractResponsibilities(vacancyDetail.description),
            requirements = extractRequirements(vacancyDetail.description),
            conditions = extractConditions(vacancyDetail.description),
            skills = vacancyDetail.skills,
            url = vacancyDetail.url,
            phone = formatPhone(vacancyDetail.contacts),
            email = vacancyDetail.contacts?.email,
        )
    }

    private fun formatSalary(salary: Salary?): String? {
        if (salary == null) return null

        return when {
            salary.from != null && salary.to != null -> "от ${salary.from} до ${salary.to} ${salary.currency ?: ""}"
            salary.from != null -> "от ${salary.from} ${salary.currency ?: ""}"
            salary.to != null -> "до ${salary.to} ${salary.currency ?: ""}"
            else -> null
        }?.trim()
    }

    private fun formatPhone(contacts: Contacts?): String? {
        return contacts?.phone?.firstOrNull()
    }

    // Временные методы для извлечения данных из описания (заглушки)
    private fun extractResponsibilities(description: String?): String? {
        return description?.takeIf { it.isNotBlank() }?.let {
            "• Разработка нового функционала\n• Участие в код-ревью\n• Оптимизация производительности"
        }
    }

    private fun extractRequirements(description: String?): String? {
        return description?.takeIf { it.isNotBlank() }?.let {
            "• Опыт разработки на Kotlin/Java\n• Знание Android SDK\n• Опыт работы с REST API"
        }
    }

    private fun extractConditions(description: String?): String? {
        return description?.takeIf { it.isNotBlank() }?.let {
            "• Удаленная работа\n• Гибкий график\n• Медицинская страховка"
        }
    }

    // Тестовая вакансия для проверки UI
    private fun createTestVacancy(): VacancyModel {
        return VacancyModel(
            id = "test",
            name = "Android Developer",
            salary = "от 150 000 ₽",
            employerName = "Яндекс",
            employerLogoUrl = "https://example.com/logo.png",
            area = "Москва",
            address = "ул. Льва Толстого, 16",
            experience = "От 1 года до 3 лет",
            employment = "Полная занятость, удаленная работа",
            description = "Мы ищем опытного Android-разработчика для работы над нашим флагманским приложением.",
            responsibilities = "• Разработка нового функционала\n• Участие в код-ревью\n• Оптимизация производительности",
            requirements = "• Опыт разработки на Kotlin/Java\n• Знание Android SDK\n• Опыт работы с REST API",
            conditions = "• Чистый офис (как уберётесь)\n• Печенье (раз в месяц и самое дешевое)\n• Бесплатный проезд (зайцем)",
            skills = listOf("Kotlin", "Android SDK", "Coroutines", "REST API", "Git"),
            url = "https://hh.ru/vacancy/123",
            phone = "8 (800) 555-35-35",
            email = "mail_@mail.ru",
        )
    }

    companion object {
        private const val CODE_404 = 404
    }
}
