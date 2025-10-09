package ru.practicum.android.diploma.db.data

import com.google.gson.Gson
import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.network.domain.models.Salary
import ru.practicum.android.diploma.network.domain.models.VacancyDetail

class VacancyDbConvertor(private val gson: Gson) {
    fun convertToVacancy(vacancyEntity: VacancyEntity): VacancyDetail {
        val salary = Salary(
            from = vacancyEntity.salaryFrom.takeIf { it.isNotEmpty() }?.toInt(),
            to = vacancyEntity.salaryTo.takeIf { it.isNotEmpty() }?.toInt(),
            currency = vacancyEntity.salaryCurrency.takeIf { it.isNotEmpty() }
        )
        return VacancyDetail(
            id = vacancyEntity.vacancyId,
            name = vacancyEntity.name,
            salary = salary,
            employerName = vacancyEntity.employerName,
            employerLogoUrl = vacancyEntity.employerLogoUrl,
            area = vacancyEntity.area,
            address = vacancyEntity.address,
            experience = vacancyEntity.experience,
            employment = vacancyEntity.employment,
            description = vacancyEntity.description,
            responsibilities = vacancyEntity.responsibilities,
            requirements = vacancyEntity.requirements,
            conditions = vacancyEntity.conditions,
            skills = vacancyEntity.skills,
            url = vacancyEntity.url,
            phone = vacancyEntity.phone,
            email = vacancyEntity.email
        )
    }

    fun convertToVacancy(vacancyDetail: VacancyDetail): VacancyEntity {
        return VacancyEntity(
            vacancyId = vacancyDetail.id,
            name = vacancyDetail.name,
            salaryFrom = vacancyDetail.salary.from?.toString() ?: "",
            salaryTo = vacancyDetail.salary.to?.toString() ?: "",
            salaryCurrency = vacancyDetail.salary.currency ?: "",
            employerName = vacancyDetail.employerName,
            employerLogoUrl = vacancyDetail.employerLogoUrl,
            area = vacancyDetail.area,
            address = vacancyDetail.address,
            experience = vacancyDetail.experience,
            employment = vacancyDetail.employment,
            description = vacancyDetail.description,
            responsibilities = vacancyDetail.responsibilities,
            requirements = vacancyDetail.requirements,
            conditions = vacancyDetail.conditions,
            skills = vacancyDetail.skills,
            url = vacancyDetail.url,
            phone = vacancyDetail.phone,
            email = vacancyDetail.email
        )
    }
}
