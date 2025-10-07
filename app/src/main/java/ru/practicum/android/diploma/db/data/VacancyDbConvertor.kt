package ru.practicum.android.diploma.db.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.network.domain.models.Salary
import ru.practicum.android.diploma.network.domain.models.Vacancy

class VacancyDbConvertor(private val gson: Gson) {
    fun map(vacancyEntity: VacancyEntity): Vacancy {
        val type = object : TypeToken<Salary>() {}.type
        val salary = gson.fromJson<Salary>(vacancyEntity.salary, type)
        return Vacancy(
            id = vacancyEntity.vacancyId.toString(),
            name = vacancyEntity.name,
            employerName = vacancyEntity.companyName,
            salaryDto = salary,
            employerLogo = vacancyEntity.employerLogo
        )
    }
}
