package ru.practicum.android.diploma.search.data.dto.converter

import ru.practicum.android.diploma.filter.domain.model.Location
import ru.practicum.android.diploma.search.domain.model.ApiResult
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Salary
import ru.practicum.android.diploma.search.domain.model.Vacancy
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse
import ru.practicum.android.diploma.search.data.dto.ApiResultDto
import ru.practicum.android.diploma.search.data.dto.response.FilterAreaDto
import ru.practicum.android.diploma.search.data.dto.response.FilterIndustryDto
import ru.practicum.android.diploma.search.data.dto.response.SalaryDto
import ru.practicum.android.diploma.search.data.dto.response.VacancyDetailDto
import ru.practicum.android.diploma.search.data.dto.response.VacancyResponseDto

object VacancyNetworkConvertor {

    private const val CODE_404 = 404

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

    fun ApiResultDto<List<FilterIndustryDto>>.convertToApiResultFilterIndustries(): ApiResult<List<FilterIndustry>> {
        return when (this) {
            is ApiResultDto.Success -> {
                if (data.isEmpty()) {
                    ApiResult.NotFound
                } else {
                    ApiResult.Success(data.map { it.convertToFilterIndustry() })
                }
            }

            is ApiResultDto.Error -> ApiResult.NotFound
            is ApiResultDto.NoInternetConnection -> ApiResult.NoInternetConnection
        }
    }

    fun ApiResultDto<List<FilterAreaDto>>.convertToApiResultFilterArea(): ApiResult<List<Location>> {
        return when (this) {
            is ApiResultDto.Success -> {
                if (data.isEmpty()) {
                    ApiResult.NotFound
                } else {
                    ApiResult.Success(data.map { it.convertToFilterArea() }.convertToLocation())
                }
            }

            is ApiResultDto.Error -> ApiResult.NotFound
            is ApiResultDto.NoInternetConnection -> ApiResult.NoInternetConnection
        }
    }

    fun ApiResultDto<VacancyDetailDto>.convertToApiResultVacancyDetail(): ApiResult<VacancyDetail> {
        return when (this) {
            is ApiResultDto.Success -> ApiResult.Success(data.convertToVacancyDetail())
            is ApiResultDto.Error -> {
                if (code == CODE_404) {
                    ApiResult.NotFound
                } else {
                    ApiResult.ServerError
                }
            }

            is ApiResultDto.NoInternetConnection -> ApiResult.NoInternetConnection
        }
    }

    fun ApiResultDto<VacancyResponseDto>.convertToApiResultVacancyResponse(): ApiResult<VacancyResponse> {
        return when (this) {
            is ApiResultDto.Success -> {
                if (data.found == 0) {
                    ApiResult.NotFound
                } else {
                    ApiResult.Success(data.convertToVacancyResponse())
                }
            }

            is ApiResultDto.Error -> ApiResult.ServerError
            is ApiResultDto.NoInternetConnection -> ApiResult.NoInternetConnection
        }
    }

    fun FilterAreaDto.convertToFilterArea(): FilterArea {
        return FilterArea(
            this.id,
            this.name,
            this.parentId,
            this.areas.map { it.convertToFilterArea() }
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

    fun FilterIndustryDto.convertToFilterIndustry(): FilterIndustry {
        return FilterIndustry(this.id, this.name)
    }

    fun FilterIndustry?.convertToFilterIndustryDto(): FilterIndustryDto? {
        return if (this != null) {
            FilterIndustryDto(this.id, this.name)
        } else {
            null
        }
    }

    fun List<FilterArea>.convertToLocation(): List<Location> {
        val locations = mutableListOf<Location>()
        this.forEach { country ->
            locations.add(Location(country.id, country.name))
            if (country.areas.isNotEmpty()) {
                country.areas.forEach { region ->
                    locations.add(
                        Location(
                            id = region.id,
                            name = region.name,
                            parent = Location(country.id, country.name)
                        )
                    )
                }
            }
        }
        return locations
    }
}
