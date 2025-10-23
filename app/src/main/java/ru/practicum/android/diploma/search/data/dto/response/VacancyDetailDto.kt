package ru.practicum.android.diploma.search.data.dto.response

data class VacancyDetailDto(
    val id: String,
    val name: String,
    val description: String,
    val salary: SalaryDto,
    val address: Address,
    val experience: Experience,
    val schedule: Schedule,
    val employment: Employment,
    val contacts: Contacts,
    val employer: Employer,
    val area: FilterAreaDto,
    val skills: List<String>,
    val url: String,
    val industry: FilterIndustryDto,
)
