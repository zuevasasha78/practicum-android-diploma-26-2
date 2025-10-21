package ru.practicum.android.diploma.favourites.data.converter

import ru.practicum.android.diploma.favourites.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.search.domain.model.VacancyDetail

object VacancyDbConvertor {

    fun convertToVacancy(vacancyEntity: VacancyEntity): Vacancy {
        val salary = Salary(
            from = vacancyEntity.salaryFrom.takeIf { it.isNotEmpty() }?.toInt(),
            to = vacancyEntity.salaryTo.takeIf { it.isNotEmpty() }?.toInt(),
            currency = vacancyEntity.salaryCurrency.takeIf { it.isNotEmpty() }
        )
        return Vacancy(
            id = vacancyEntity.vacancyId,
            name = vacancyEntity.name,
            salaryDto = salary,
            employerName = vacancyEntity.employerName,
            employerLogo = vacancyEntity.employerLogoUrl ?: ""
        )
    }

    fun convertToVacancyDetail(vacancyEntity: VacancyEntity): VacancyDetail {
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
