package ru.practicum.android.diploma.network.data

import ru.practicum.android.diploma.filter.domain.Workplace
import ru.practicum.android.diploma.filter.domain.WorkplaceType
import ru.practicum.android.diploma.network.data.dto.requests.VacanciesFilterDto
import ru.practicum.android.diploma.network.data.dto.response.FilterArea
import ru.practicum.android.diploma.network.data.dto.response.SalaryDto
import ru.practicum.android.diploma.network.data.dto.response.VacancyDetailDto
import ru.practicum.android.diploma.network.data.dto.response.VacancyResponseDto
import ru.practicum.android.diploma.network.domain.models.Salary
import ru.practicum.android.diploma.network.domain.models.Vacancy
import ru.practicum.android.diploma.network.domain.models.VacancyDetail
import ru.practicum.android.diploma.network.domain.models.VacancyResponse
import ru.practicum.android.diploma.network.domain.models.requests.VacanciesFilter

object VacancyNetworkConvertor {

    fun VacancyDetailDto.convertToVacancy(): Vacancy {
        return Vacancy(
            this.id,
            this.name,
            this.employer.name,
            this.salary.convertToSalary(),
            this.employer.logo
        )
    }

    fun VacancyResponseDto.convertToVacancyResponse(): VacancyResponse {
        return VacancyResponse(
            this.found,
            this.pages,
            this.page,
            this.items.map {
                it.convertToVacancy()
            }
        )
    }

    fun ApiResult<VacancyResponseDto>.convertToApiResultVacancyResponse(): ApiResult<VacancyResponse> {
        return when (this) {
            is ApiResult.Success -> ApiResult.Success(data.convertToVacancyResponse())
            is ApiResult.Error -> ApiResult.Error(code)
            is ApiResult.NoInternetConnection -> ApiResult.NoInternetConnection
        }
    }

    fun VacanciesFilter.convertToVacanciesFilterDto(): VacanciesFilterDto {
        return VacanciesFilterDto(
            this.area,
            this.industry,
            this.text,
            this.salary,
            this.page,
            this.onlyWithSalary
        )
    }

    fun SalaryDto.convertToSalary(): Salary {
        return Salary(
            this.from,
            this.to,
            this.currency
        )
    }

    fun VacancyDetailDto.convertToVacancyDetail(): VacancyDetail {
        return VacancyDetail(
            id = this.id,
            name = this.name,
            salary = this.salary.convertToSalary(),
            employerName = this.employer.name,
            employerLogoUrl = this.employer.logo,
            area = this.area.name,
            address = this.address.city,
            experience = this.experience.name,
            employment = this.employment.name,
            description = this.description,
            responsibilities = this.description,
            requirements = this.description,
            conditions = this.description,
            skills = this.skills.joinToString("\n") { "• $it" },
            url = this.url
        )
    }

    fun List<VacancyDetail>.convertToVacancyList(): List<Vacancy> {
        return this.map { vacancyDetail ->
            Vacancy(
                id = vacancyDetail.id,
                name = vacancyDetail.name,
                employerName = vacancyDetail.employerName,
                salaryDto = vacancyDetail.salary,
                employerLogo = vacancyDetail.employerLogoUrl.let { it } ?: "",
            )
        }
    }

    fun List<FilterArea>.convertToWorkplace(): List<Workplace> {
        return this.map { area ->
            val type = if (area.parentId == null) {
                WorkplaceType.COUNTRY
            } else {
                WorkplaceType.REGION
            }
            Workplace(
                id = area.id,
                value = area.name,
                type = type
            )
        }
    }
}
