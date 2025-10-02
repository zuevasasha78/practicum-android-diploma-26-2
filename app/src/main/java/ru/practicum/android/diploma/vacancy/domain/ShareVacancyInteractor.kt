package ru.practicum.android.diploma.vacancy.domain

import ru.practicum.android.diploma.network.data.dto.response.VacancyDetail

class ShareVacancyInteractor {

    fun prepareShareContent(vacancy: VacancyDetail): String {
        return buildString {
            append("Вакансия: ${vacancy.name}\n")
            append("Компания: ${vacancy.employer.name}\n")
            append("Зарплата: ${(vacancy.salary)}\n")
            append("Город: ${vacancy.area.name}\n")
            append("Ссылка: ${vacancy.url}")
        }
    }
}
